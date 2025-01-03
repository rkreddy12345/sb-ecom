package com.rkecom.data.product.repository;

import com.rkecom.data.repository.BaseRepository;
import com.rkecom.db.entity.product.Category;
import com.rkecom.db.entity.product.Product;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends BaseRepository <Product, Long> {
    List<Product> findByCategoryOrderByPriceAsc( Category category);
    List<Product> findByNameLikeIgnoreCase(String keyword);
}
