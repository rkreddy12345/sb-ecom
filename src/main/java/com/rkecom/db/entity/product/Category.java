package com.rkecom.db.entity.product;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table( name = "CATEGORY" )
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    @Setter(AccessLevel.NONE)
    private Long categoryId;

    @Column(name="category_name", nullable=false, length=50)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List <Product> products;
}
