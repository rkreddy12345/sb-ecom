package com.rkecom.db.entity.user;

import com.rkecom.db.entity.product.Cart;
import com.rkecom.db.entity.product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor ( access = AccessLevel.PROTECTED )
@Table(name = "APP_USER", uniqueConstraints = {
        @UniqueConstraint ( columnNames = "USER_NAME"),
        @UniqueConstraint ( columnNames = "EMAIL")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter( AccessLevel.NONE)
    @Column(name = "USER_ID")
    private Long userId;
    @Column (name = "USER_NAME", nullable = false, length = 20)
    private String userName;
    @Column (name = "PASSWORD", nullable = false, length = 120)
    private String password;
    @Column (name = "EMAIL", nullable = false, unique = true, length = 50)
    private String email;

    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "USER_ROLE",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID"))
    private List <Role> roles = new ArrayList <> ();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List< Product > products = new ArrayList <> ();

    @OneToOne(mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true)
    private Cart cart;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "USER_ADDRESS",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ADDRESS_ID"))
    private List<Address> addresses = new ArrayList <> ();
}
