package com.rkecom.crud.product.service.impl;

import com.rkecom.core.exception.ApiException;
import com.rkecom.core.exception.ResourceNotFoundException;
import com.rkecom.core.util.ResourceConstants;
import com.rkecom.crud.product.service.CartService;
import com.rkecom.data.product.repository.CartItemRepository;
import com.rkecom.data.product.repository.CartRepository;
import com.rkecom.data.product.repository.ProductRepository;
import com.rkecom.db.entity.product.Cart;
import com.rkecom.db.entity.product.CartItem;
import com.rkecom.db.entity.product.Product;
import com.rkecom.objects.product.mapper.CartMapper;
import com.rkecom.objects.user.mapper.UserMapper;
import com.rkecom.security.auth.util.UserDetailsUtil;
import com.rkecom.web.product.model.CartModel;
import com.rkecom.web.user.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor (onConstructor_ = {@Autowired})
@Transactional (readOnly = true)
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final UserDetailsUtil userDetailsUtil;
    private final UserMapper userMapper;
    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;

    @Transactional
    @Override
    public CartModel addProductToCart( Long productId) {
        Cart cart = getTheCart();
        if (cartItemRepository.findByProductIdAndCartId(productId, cart.getCartId()).isPresent()) {
            throw new ApiException ("This item already exists in the cart. update the quantity instead.");
        }
        Product product = getProductById(productId);
        if (product.getQuantity() < 1) {
            throw new ApiException("The requested item is out of stock.");
        }
        CartItem cartItem= buildCartItem(cart, product, 1);
        cartItemRepository.save(cartItem);  // Explicitly save the CartItem to make it managed
        cart.addItem ( cartItem );
        Cart savedCart= cartRepository.save(cart);
        return cartMapper.toModel().apply ( savedCart );
    }

    @Transactional
    @Override
    public CartModel updateProductQtyInCart(Long productId, Integer quantity) {
        Cart cart = getTheCart();
        Optional < CartItem > existingCartItem = cartItemRepository.findByProductIdAndCartId(productId, cart.getCartId());
        Product product = getProductById(productId);
        if (existingCartItem.isPresent()) {
            CartItem item = existingCartItem.get();
            int newQuantity = item.getQuantity() + quantity;

            // Ensure quantity is valid
            if (newQuantity < 0) throw new ApiException("Quantity cannot be negative.");
            if (newQuantity == 0) cart.removeItem ( item ); // Remove item if quantity is 0
            else {
                // Update quantity, price, and discount
                item.setQuantity(newQuantity);
                item.setPrice(product.getSpecialPrice());
                item.setDiscount(product.getDiscount());

                // Explicitly save item to ensure update in DB
                cartItemRepository.save(item);

                // Ensure total price is updated
                cart.updateTotalPrice();
            }

        } else {
            // Validate stock for new item
            if (product.getQuantity() < quantity) throw new ApiException("Insufficient stock.");
            if (quantity < 0) throw new ApiException("Quantity cannot be negative, add the item to cart.");
            CartItem cartItem = buildCartItem(cart, product, quantity);
            cart.addItem ( cartItem );
            cartItemRepository.save(cartItem);
        }

        // Save and return updated cart
        return cartMapper.toModel().apply(cartRepository.save(cart));
    }

    @Override
    public CartModel getUserCart(String email) {
        // Fetch user's cart by email
        Cart cart = cartRepository.findCartByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException ( ResourceConstants.CART, "email", email));
        return cartMapper.toModel().apply(cart);
    }

    @Override
    @Transactional
    public CartModel deleteProductFromCart(Long productId) {
        String email = getCurrentUser().getEmail();

        //  Fetch Cart (with cart items)
        Cart cart = cartRepository.findCartByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.CART, "email", email));

        //  Find CartItem from the already loaded Cart items (NO extra DB call)
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.CART_ITEM, "productId", productId));

        cart.removeItem(cartItem); //  Removes the item from cart
        cartRepository.save(cart); //  Saves the cart

        return cartMapper.toModel().apply(cart);
    }


    @Override
    @Transactional
    public void deleteProductFromCart ( Long productId, Long cartId ) {
        Cart cart=cartRepository.findById ( cartId )
                .orElseThrow (()->new ResourceNotFoundException ( ResourceConstants.CART, "id", cartId ));
        CartItem cartItem = cartItemRepository.findByProductIdAndCartId ( productId, cartId )
                .orElseThrow (()->new ResourceNotFoundException ( ResourceConstants.CART_ITEM, "productId", productId ));
        BigDecimal cartPrice =  cart.getTotalPrice ().subtract (cartItem.getPrice ().multiply ( BigDecimal.valueOf ( cartItem.getQuantity () ) ));
        cart.setTotalPrice ( cartPrice );
        cartRepository.save ( cart );
        cartItemRepository.deleteCartItemByProductIdAndCartItemId(productId, cartId);
    }

    @Override
    @Transactional
    public void updateProductInCarts ( Long cartId, Long productId ) {
        Cart cart=cartRepository.findById ( cartId )
                .orElseThrow (()->new ResourceNotFoundException ( ResourceConstants.CART, "id", cartId ));

        CartItem cartItem = cartItemRepository.findByProductIdAndCartId ( productId, cartId )
                .orElseThrow (()->new ResourceNotFoundException ( ResourceConstants.CART_ITEM, "id", productId ));
        Product product=getProductById (productId);

        BigDecimal cartPrice =  cart.getTotalPrice ().subtract (cartItem.getPrice ().multiply ( BigDecimal.valueOf ( cartItem.getQuantity () ) ));
        cartItem.setPrice ( product.getSpecialPrice () );
        cartItem.setDiscount ( product.getDiscount () );
        cart.setTotalPrice ( cartPrice.add ( cartItem.getPrice ().multiply ( BigDecimal.valueOf ( cartItem.getQuantity () ) ) ) );
        cartRepository.save(cart);
    }


    private Cart getTheCart() {
        // Get cart or create a new one if not exists
        return cartRepository.findCartByEmail(getCurrentUser().getEmail()).orElseGet(this::createNewCart);
    }

    private Cart createNewCart() {
        Cart cart = new Cart ();
        cart.setUser ( userMapper.toEntity().apply(getCurrentUser()) );
        cart.setCartItems ( new ArrayList <> () );
        cart.setTotalPrice ( BigDecimal.ZERO );
        return  cart;
    }

    private Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(ResourceConstants.PRODUCT, "id", productId));
    }

    private CartItem buildCartItem(Cart cart, Product product, int quantity) {
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        cartItem.setPrice(product.getSpecialPrice());
        cartItem.setDiscount(product.getDiscount());
        return cartItem;
    }

    private UserModel getCurrentUser() {
        // Fetch current authenticated user
        return userDetailsUtil.getCurrentUser()
                .orElseThrow(() -> new AccessDeniedException ("Access denied: User is not authenticated"));
    }
}
