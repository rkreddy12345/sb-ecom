package com.rkecom.controller;

import com.rkecom.core.response.ApiResponse;
import com.rkecom.core.util.Pagination;
import com.rkecom.crud.service.ProductService;
import com.rkecom.ui.model.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
    public ResponseEntity< ApiResponse<ProductModel> > getAllProducts(
            @RequestParam(defaultValue = Pagination.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(defaultValue = Pagination.DEFAULT_PAGE_SIZE) Integer size,
            @RequestParam(defaultValue = Pagination.SORT_BY_ID) String sortBy,
            @RequestParam(defaultValue = Pagination.SORT_IN_ASC) String sortOrder) {
        return ResponseEntity.ok().body(productService.getAllProducts(page, size, sortBy, sortOrder));
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ApiResponse<ProductModel>> getProductsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok().body(productService.getProductsByCategory(categoryId));
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ApiResponse<ProductModel>> getProductsByKeyword(@PathVariable String keyword) {
        return new ResponseEntity <> ( productService.searchProductsByKeyword ( keyword ), HttpStatus.FOUND );
    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductModel> updateProduct( @PathVariable Long productId, @RequestBody ProductModel productModel) {
        return ResponseEntity.ok (productService.updateProductById ( productId, productModel ));
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductModel> deleteProductById(@PathVariable Long productId) {
        return ResponseEntity.ok (productService.deleteProductById(productId));
    }

    @PutMapping("/admin/products/{productId}/image")
    public ResponseEntity<ProductModel> updateProductImage(@PathVariable Long productId, @RequestParam MultipartFile image) throws IOException {
        return ResponseEntity.ok(productService.updateProductImage ( productId, image ));
    }

}
