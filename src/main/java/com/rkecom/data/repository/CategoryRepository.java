package com.rkecom.data.repository;

import com.rkecom.db.entity.Category;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends BaseRepository < Category, Long > {
    public Optional <Category> findByName ( String name );
}
