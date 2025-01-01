package com.rkecom.db.entity.product;

import com.rkecom.db.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PRODUCT")
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    @Column(name = "NAME", nullable = false, length = 50)
    private String name;
    @Column(name = "DESCRIPTION", nullable = false, length = 100)
    private String description;
    private String image;
    private Integer quantity;
    private Double price;
    private Double discount;
    @Column(name = "SPECIAL_PRICE")
    private Double specialPrice;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "SELLER_ID")
    private User user;
}
