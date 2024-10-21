package com.rkecom.crud.service.impl;

import com.rkecom.core.response.ApiResponse;
import com.rkecom.db.entity.Category;
import com.rkecom.db.entity.Product;
import com.rkecom.core.exception.ApiException;
import com.rkecom.core.exception.ResourceNotFoundException;
import com.rkecom.objects.mapper.ProductMapper;
import com.rkecom.data.repository.CategoryRepository;
import com.rkecom.data.repository.ProductRepository;
import com.rkecom.crud.service.ProductService;
import com.rkecom.ui.model.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
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

    @Override
    public ApiResponse < ProductModel > getProductsByCategory ( Long categoryId ) {
        Category category=categoryRepository.findById ( categoryId ).orElseThrow (
                ()->new ResourceNotFoundException ("category", "id", categoryId)
        );
        List<Product> products=productRepository.findByCategoryOrderByPriceAsc ( category );
        if(products.isEmpty ()){
            throw new ApiException ( "No products found in this category." );
        }
        List<ProductModel> productModels=products.stream ().map ( ProductMapper.toModel).toList ();
        return ApiResponse.<ProductModel>builder ()
                .content ( productModels )
                .build ();
    }

    @Override
    public ApiResponse < ProductModel > searchProductsByKeyword ( String keyword ) {
        List<Product> products=productRepository.findByNameLikeIgnoreCase ( '%'+keyword+'%' );
        if(products.isEmpty ()){
            throw new ApiException ( "No products found." );
        }
        List<ProductModel> productModels = products.stream ().map ( ProductMapper.toModel ).toList ();
        return ApiResponse.<ProductModel>builder ().content ( productModels ).build ();
    }
}
