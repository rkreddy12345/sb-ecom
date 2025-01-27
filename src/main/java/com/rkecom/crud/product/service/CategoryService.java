package com.rkecom.crud.product.service;

import com.rkecom.crud.core.service.BaseService;
import com.rkecom.web.product.model.CategoryModel;
import com.rkecom.core.response.ApiResponse;

public interface CategoryService extends BaseService {
    CategoryModel createCategory( CategoryModel category);
    ApiResponse<CategoryModel> getAllCategories( Integer page, Integer size, String sortBy, String order);
    CategoryModel updateCategory( CategoryModel category, Long id);
    CategoryModel deleteCategoryById( Long id);
    ApiResponse<CategoryModel> getAllCategories ( Integer page, Integer size );
}
