package com.rkecom.controller;

import com.rkecom.core.response.ApiResponse;
import com.rkecom.service.ProductService;
import com.rkecom.ui.model.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ProductController {
    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/admin/categories/{categoryId}/products")
    public ResponseEntity< ProductModel > addProduct( @PathVariable Long categoryId, @RequestBody ProductModel productModel) {
        return ResponseEntity.ok().body(productService.addProduct(categoryId, productModel));
    }

    @GetMapping("/public/products")
    public ResponseEntity< ApiResponse<ProductModel> > getAllProducts() {
        return ResponseEntity.ok().body(productService.getAllProducts());
    }

    @GetMapping("/admin/categories/{categoryId}/products")
    public ResponseEntity<ApiResponse<ProductModel>> getProductsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok().body(productService.getProductsByCategory(categoryId));
    }
}
