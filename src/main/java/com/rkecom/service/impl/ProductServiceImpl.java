package com.rkecom.service.impl;

import com.rkecom.core.response.ApiResponse;
import com.rkecom.db.entity.Category;
import com.rkecom.db.entity.Product;
import com.rkecom.exception.ApiException;
import com.rkecom.exception.ResourceNotFoundException;
import com.rkecom.objects.mapper.ProductMapper;
import com.rkecom.repository.CategoryRepository;
import com.rkecom.repository.ProductRepository;
import com.rkecom.service.ProductService;
import com.rkecom.ui.model.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository=categoryRepository;
    }

    @Override
    @Transactional
    public ProductModel addProduct ( Long categoryId, ProductModel productModel ) {
        Product product = ProductMapper.toEntity.apply ( productModel );
        Category category=categoryRepository.findById(categoryId).orElseThrow (
                ()->new ResourceNotFoundException ("category", "id", categoryId)
        );
        product.setCategory(category);
        product.setSpecialPrice ( productModel.getPrice ()*(productModel.getDiscount ()*0.01) );
        Product savedProduct=productRepository.save ( product );
        return ProductMapper.toModel.apply ( savedProduct );
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse< ProductModel > getAllProducts ( ) {
        List <Product> products=productRepository.findAll ();
        if(products.isEmpty ()){
            throw new ApiException ( "No products found" );
        }else{
            List< ProductModel > productModels = products.stream ()
                    .map ( ProductMapper.toModel )
                    .toList ();
            return ApiResponse.<ProductModel>builder ()
                    .content ( productModels )
                    .build ();
        }
    }
}
