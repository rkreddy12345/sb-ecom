package com.rkecom.crud.product.service;

import com.rkecom.core.response.ApiResponse;
import com.rkecom.crud.core.service.BaseService;
import com.rkecom.web.product.model.ProductModel;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService extends BaseService {
    ProductModel addProduct(Long categoryId, ProductModel productModel);
    ApiResponse<ProductModel> getAllProducts(Integer pageNo, Integer pageSize, String sortBy, String sortOrder);
    ApiResponse<ProductModel> getProductsByCategory(Long categoryId);
    ApiResponse<ProductModel> searchProductsByKeyword(String keyword);
    ProductModel updateProductById(Long id, ProductModel productModel);

    ProductModel deleteProductById ( Long productId );

    ProductModel updateProductImage ( Long productId, MultipartFile image ) throws IOException;
    ProductModel getProductById(Long productId);
}
