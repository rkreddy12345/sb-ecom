package com.rkecom.data.product.repository;

import com.rkecom.core.data.repository.BaseRepository;
import com.rkecom.db.entity.product.Category;
import com.rkecom.db.entity.product.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends BaseRepository <Product, Long> {
    Optional<Product> findByName(String name);
    List<Product> findByCategoryOrderByPriceAsc( Category category);
    List<Product> findByNameLikeIgnoreCase(String keyword);
}
