package com.rkecom.db.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table( name = "CATEGORIES" )
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(name="category_name")
    private String name;

}
