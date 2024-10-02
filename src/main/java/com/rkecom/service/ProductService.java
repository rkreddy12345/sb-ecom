package com.rkecom.service;

import com.rkecom.core.response.ApiResponse;
import com.rkecom.ui.model.ProductModel;

public interface ProductService {
    ProductModel addProduct(Long categoryId, ProductModel productModel);
    ApiResponse<ProductModel> getAllProducts();
}
