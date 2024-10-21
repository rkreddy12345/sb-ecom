package com.rkecom.data.repository;

import com.rkecom.db.entity.Category;
import com.rkecom.db.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends BaseRepository<Product, Long> {
    List<Product> findByCategoryOrderByPriceAsc( Category category);
    List<Product> findByNameLikeIgnoreCase(String keyword);
}
