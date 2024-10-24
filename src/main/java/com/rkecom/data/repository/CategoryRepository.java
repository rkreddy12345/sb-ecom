package com.rkecom.data.repository;

import com.rkecom.db.entity.Category;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends BaseRepository < Category, Long > {

}
