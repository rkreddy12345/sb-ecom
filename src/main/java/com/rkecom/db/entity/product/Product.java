package com.rkecom.db.entity.product;

import com.rkecom.db.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PRODUCT")
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 100)
    private String description;
    private String image;
    private Integer quantity;
    @Column(precision = 15, scale = 2)
    private BigDecimal price;
    private BigDecimal discount;
    @Column(name = "special_price", precision = 15, scale = 2)
    private BigDecimal specialPrice;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User user;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    private List <CartItem> cartItems=new ArrayList <> ();
}
