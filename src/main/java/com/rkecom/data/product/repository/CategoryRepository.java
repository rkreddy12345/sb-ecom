package com.rkecom.data.product.repository;

import com.rkecom.core.data.repository.BaseRepository;
import com.rkecom.db.entity.product.Category;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends BaseRepository < Category, Long > {
    Optional<Category> findByName(String name);
}
