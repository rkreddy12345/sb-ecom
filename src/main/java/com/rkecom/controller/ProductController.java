package com.rkecom.controller;

import com.rkecom.response.ProductResponse;
import com.rkecom.service.ProductService;
import com.rkecom.ui.model.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ProductController {
    private ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity< ProductModel > addProduct( @PathVariable Long categoryId, @RequestBody ProductModel productModel) {
        return ResponseEntity.ok().body(productService.addProduct(categoryId, productModel));
    }

    @GetMapping("/public/products")
    public ResponseEntity< ProductResponse > getAllProducts() {
        return ResponseEntity.ok().body(productService.getAllProducts());
    }
}