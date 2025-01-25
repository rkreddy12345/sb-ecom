package com.rkecom.db.entity.product;

import com.rkecom.db.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REMOVE}, orphanRemoval = true)
    private List <CartItem> cartItems=new ArrayList <> ();
    @Column(name = "total_price")
    private Double totalPrice;

}
