package com.rkecom.db.entity.product;

import com.rkecom.db.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    @ToString.Exclude
    private Category category;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User user;

    @OneToMany(mappedBy = "product")
    @ToString.Exclude
    private List <CartItem> cartItems=new ArrayList <> ();

    public void updateSpecialPrice() {
        if (this.price != null && this.discount != null) {
            BigDecimal discountAmount = this.price
                    .multiply(this.discount)
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP );
            this.specialPrice = this.price.subtract(discountAmount);
        }
    }

}
