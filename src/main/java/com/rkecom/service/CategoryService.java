package com.rkecom.service;

import com.rkecom.ui.model.CategoryModel;
import com.rkecom.core.response.ApiResponse;

public interface CategoryService {
    CategoryModel createCategory( CategoryModel category);
    ApiResponse<CategoryModel> getAllCategories( Integer page, Integer size, String sortBy, String order);
    CategoryModel updateCategory( CategoryModel category, Long id);
    CategoryModel deleteCategoryById( Long id);

    ApiResponse<CategoryModel> getAllCategories ( Integer page, Integer size );
}
