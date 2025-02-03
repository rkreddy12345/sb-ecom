package com.rkecom.db.entity.product;

import com.rkecom.db.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    @Column(name = "cart_id")
    @Setter (AccessLevel.NONE)
    private Long cartId;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List <CartItem> cartItems=new ArrayList <> ();
    @Column(name = "total_price", precision = 15, scale = 2)
    private BigDecimal totalPrice=BigDecimal.ZERO;

    public void addItem(CartItem item) {
        cartItems.add(item);
        item.setCart(this);// Synchronize the bidirectional relationship
        updateTotalPrice();
    }

    public void removeItem(CartItem item) {
        cartItems.remove(item);
        item.setCart ( null );// Clear the Cart reference in the CartItem
        updateTotalPrice();
    }

    public void updateTotalPrice() {
        this.totalPrice = cartItems.stream()
                .map(item -> item.getPrice()
                        .subtract(item.getDiscount())
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
