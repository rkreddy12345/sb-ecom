package com.rkecom.service;

import com.rkecom.ui.model.CategoryModel;
import com.rkecom.payload.CategoryResponse;

public interface CategoryService {
    CategoryModel createCategory( CategoryModel category);
    CategoryResponse getAllCategories(Integer page, Integer size, String sortBy, String order);
    CategoryModel updateCategory( CategoryModel category, Long id);
    CategoryModel deleteCategoryById( Long id);
}
