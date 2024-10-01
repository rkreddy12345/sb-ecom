package com.rkecom.service;

import com.rkecom.response.ProductResponse;
import com.rkecom.ui.model.ProductModel;

public interface ProductService {
    ProductModel addProduct(Long categoryId, ProductModel productModel);
    ProductResponse getAllProducts();
}
