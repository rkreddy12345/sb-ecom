package com.rkecom.crud.service;

import com.rkecom.core.response.ApiResponse;
import com.rkecom.ui.model.ProductModel;

public interface ProductService extends BaseService {
    ProductModel addProduct(Long categoryId, ProductModel productModel);
    ApiResponse<ProductModel> getAllProducts();
    ApiResponse<ProductModel> getProductsByCategory(Long categoryId);
    ApiResponse<ProductModel> searchProductsByKeyword(String keyword);
}
