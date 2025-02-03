package com.rkecom.data.product.repository;

import com.rkecom.core.data.repository.BaseRepository;
import com.rkecom.db.entity.product.CartItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartItemRepository extends BaseRepository< CartItem,Long > {
    @Query("select ci from CartItem ci where ci.product.productId=:productId and ci.cart.cartId=:cartId")
    Optional <CartItem> findByProductIdAndCartId( @Param ( "productId" ) Long productId, @Param ( "cartId" ) Long cartId );
}
