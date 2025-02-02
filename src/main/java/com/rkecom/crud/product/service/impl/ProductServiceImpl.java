package com.rkecom.crud.product.service.impl;

import com.rkecom.core.exception.ApiException;
import com.rkecom.core.exception.ResourceNotFoundException;
import com.rkecom.core.response.ApiResponse;
import com.rkecom.core.response.util.ApiResponseUtil;
import com.rkecom.core.util.PaginationUtil;
import com.rkecom.core.util.ResourceConstants;
import com.rkecom.crud.core.service.FileService;
import com.rkecom.crud.product.service.ProductService;
import com.rkecom.data.product.repository.CategoryRepository;
import com.rkecom.data.product.repository.ProductRepository;
import com.rkecom.db.entity.product.Category;
import com.rkecom.db.entity.product.Product;
import com.rkecom.objects.product.mapper.ProductMapper;
import com.rkecom.web.product.model.ProductModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor (onConstructor_ = {@Autowired})
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FileService fileService;
    private final ProductMapper productMapper;

    @Value ( "${project.images}" )
    private String path;

    @Override
    @Transactional
    public ProductModel addProduct ( Long categoryId, ProductModel productModel ) {
        Category category=categoryRepository.findById(categoryId).orElseThrow (
                ()->new ResourceNotFoundException (ResourceConstants.CATEGORY, "id", categoryId)
        );
        if(isProductExistsWithName ( productModel.getName (), null )){
            throw new ApiException ( "product already exists with name - " + productModel.getName () );
        }
        Product product=productMapper.toEntity().apply ( productModel );
        product.setCategory(category);
        product.setSpecialPrice(
                productModel.getPrice().multiply(
                        productModel.getDiscount().multiply( BigDecimal.valueOf(0.01))
                )
        );

        return productMapper.toModel().apply ( productRepository.save ( product ) );
    }

    @Override
    public ApiResponse< ProductModel > getAllProducts (Integer page, Integer size, String sortBy, String sortOrder) {
        Sort sort=sortOrder.equalsIgnoreCase ( PaginationUtil.SORT_IN_ASC )
                ?Sort.by ( Sort.Direction.ASC, sortBy )
                :Sort.by ( Sort.Direction.DESC, sortBy );
        Page<Product> productPage = productRepository.findAll ( PageRequest.of ( page, size, sort ) );
        return buildProductApiResponse ( productPage );
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
        List<ProductModel> productModels=products.stream ().map ( productMapper.toModel()).toList ();
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
        List<ProductModel> productModels = products.stream ().map ( productMapper.toModel() ).toList ();
        return ApiResponse.<ProductModel>builder ().content ( productModels ).build ();
    }

    @Override
    @Transactional
    public ProductModel updateProductById ( Long id, ProductModel productModel ) {
        Product existingProduct=productRepository.findById ( id )
                .orElseThrow ( ()->new ResourceNotFoundException ( ResourceConstants.PRODUCT, "id", id) );
        if(isProductExistsWithName ( productModel.getName (), id )){
            throw new ApiException ( "Product already exists with name - "+productModel.getName () );
        }
        Product updatedEntity = productMapper.toUpdatedEntity().apply ( existingProduct, productModel );
        return productMapper.toModel().apply ( productRepository.save ( updatedEntity ) );
    }

    @Override
    @Transactional
    public ProductModel deleteProductById ( Long productId ) {
        Product product = productRepository.findById ( productId ).orElseThrow (
                ()->new ResourceNotFoundException (ResourceConstants.PRODUCT, "id", productId)
        );
        productRepository.deleteById ( productId );
        return productMapper.toModel().apply ( product );
    }

    @Override
    @Transactional
    public ProductModel updateProductImage ( Long productId, MultipartFile image ) throws IOException {
        Product product = productRepository.findById ( productId ).orElseThrow (
                ()->new ResourceNotFoundException (ResourceConstants.PRODUCT, "id", productId)
        );
        String fileName= fileService.uploadFile (path, image);
        product.setImage(fileName);
        return productMapper.toModel().apply ( productRepository.save ( product ) );
    }

    @Override
    public ProductModel getProductById ( Long productId ) {
        Product product=productRepository.findById ( productId ).orElseThrow (
                ()->new ResourceNotFoundException (ResourceConstants.PRODUCT, "id", productId)
        );
        return productMapper.toModel().apply ( product );
    }

    private boolean isProductExistsWithName(String name, Long id){
        Optional <Product> product = productRepository.findByName(name);
        return product.isPresent() && (id==null || !product.get().getProductId ().equals(id));
    }

    private ApiResponse < ProductModel > buildProductApiResponse ( Page < Product > productPage ) {
        List <Product> products = productPage.getContent ();
        if(products.isEmpty()) {
            throw new ApiException ( "No products found" );
        }else{
            List<ProductModel> productModels = products.stream()
                    .map ( productMapper.toModel() )
                    .toList ();

            return ApiResponseUtil.buildApiResponse ( productModels, productPage );
        }
    }
}
