package com.rkecom.data.product.repository;

import com.rkecom.core.data.repository.BaseRepository;
import com.rkecom.db.entity.product.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends BaseRepository < Category, Long > {

}
