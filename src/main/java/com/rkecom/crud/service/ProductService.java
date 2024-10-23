package com.rkecom.crud.service;

import com.rkecom.core.response.ApiResponse;
import com.rkecom.ui.model.ProductModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService extends BaseService {
    ProductModel addProduct(Long categoryId, ProductModel productModel);
    ApiResponse<ProductModel> getAllProducts();
    ApiResponse<ProductModel> getProductsByCategory(Long categoryId);
    ApiResponse<ProductModel> searchProductsByKeyword(String keyword);
    ProductModel updateProductById(Long id, ProductModel productModel);

    ProductModel deleteProductById ( Long productId );

    ProductModel updateProductImage ( Long productId, MultipartFile image ) throws IOException;
}
