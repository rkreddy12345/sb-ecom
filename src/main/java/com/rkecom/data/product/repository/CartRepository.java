package com.rkecom.data.product.repository;

import com.rkecom.core.data.repository.BaseRepository;
import com.rkecom.db.entity.product.Cart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends BaseRepository< Cart, Long> {
    @Query("select c from Cart c where c.user.email=:email")
    Optional <Cart> findCartByEmail( @Param("email") String email);

    @Query("select c from Cart c join fetch c.cartItems ci join fetch ci.product p where p.productId=:productId")
    Optional< List <Cart> > findCartsByProductId( @Param("productId") Long productId);
}
