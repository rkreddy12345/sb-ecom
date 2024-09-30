package com.rkecom.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @JsonIgnore
    private Long id;

    @Column(name="category_name")
    @NotBlank
    @Size (min = 2, message = "should contain minimum 2 characters")
    private String name;

}
