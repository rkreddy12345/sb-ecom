package com.rkecom.crud.service.impl;

import com.rkecom.core.exception.ApiException;
import com.rkecom.core.exception.ResourceNotFoundException;
import com.rkecom.core.response.ApiResponse;
import com.rkecom.crud.service.FileService;
import com.rkecom.crud.service.ProductService;
import com.rkecom.data.repository.CategoryRepository;
import com.rkecom.data.repository.ProductRepository;
import com.rkecom.db.entity.Category;
import com.rkecom.db.entity.Product;
import com.rkecom.objects.mapper.ProductMapper;
import com.rkecom.ui.model.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FileService fileService;

    @Value ( "${project.images}" )
    private String path;

    @Autowired
    public ProductServiceImpl( ProductRepository productRepository, CategoryRepository categoryRepository, FileService fileService ) {
        this.productRepository = productRepository;
        this.categoryRepository=categoryRepository;
        this.fileService = fileService;
    }

    @Override
    @Transactional
    public ProductModel addProduct ( Long categoryId, ProductModel productModel ) {
        Product product = ProductMapper.toEntity.apply ( productModel );
        Product savedProduct=null;
        Category category=categoryRepository.findById(categoryId).orElseThrow (
                ()->new ResourceNotFoundException ("category", "id", categoryId)
        );
        if(!isProductExistsWithName ( productModel.getName () )){
            product.setCategory(category);
            product.setSpecialPrice ( productModel.getPrice ()*(productModel.getDiscount ()*0.01) );
            savedProduct=productRepository.save ( product );
        }

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

    @Override
    @Transactional
    public ProductModel updateProductById ( Long id, ProductModel productModel ) {
        Product existingProduct=productRepository.findById ( id ).orElseThrow (
                ()->new ResourceNotFoundException ("product", "id", id)
        );
        Product product=null;
        if(!isProductExistsWithName ( productModel.getName () )){
            Product updatedEntity = ProductMapper.toUpdatedEntity.apply (existingProduct, productModel);
            product=productRepository.save(updatedEntity);
        }

        return ProductMapper.toModel.apply (product );
    }

    @Override
    @Transactional
    public ProductModel deleteProductById ( Long productId ) {
        Product product = productRepository.findById ( productId ).orElseThrow (
                ()->new ResourceNotFoundException ("product", "id", productId)
        );
        productRepository.deleteById ( productId );
        return ProductMapper.toModel.apply ( product );
    }

    @Override
    @Transactional
    public ProductModel updateProductImage ( Long productId, MultipartFile image ) throws IOException {
        Product product = productRepository.findById ( productId ).orElseThrow (
                ()->new ResourceNotFoundException ("product", "id", productId)
        );
        String fileName= fileService.uploadFile (path, image);
        product.setImage(fileName);
        return ProductMapper.toModel.apply ( productRepository.save ( product ) );
    }

    private boolean isProductExistsWithName(String name){
        Optional <Product> product = productRepository.findByName(name);
        if(product.isPresent()) {
            throw new ApiException ( "product already exists with name - " + name );
        }
        return false;
    }
}
