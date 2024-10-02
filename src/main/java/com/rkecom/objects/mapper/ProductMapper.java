package com.rkecom.objects.mapper;

import com.rkecom.db.entity.Product;
import com.rkecom.ui.model.ProductModel;

import java.util.function.BiFunction;
import java.util.function.Function;

public class ProductMapper {

    public static final Function< ProductModel, Product > toEntity = productModel -> {
        return Product.builder()
                .name(productModel.getName())
                .description ( productModel.getDescription() )
                .image(productModel.getImage())
                .price(productModel.getPrice())
                .discount ( productModel.getDiscount() )
                .specialPrice ( productModel.getSpecialPrice() )
                .quantity ( productModel.getQuantity() )
                .category ( productModel.getCategory() )
                .build();
    };

    public static final Function<Product, ProductModel> toModel = product -> {
        return ProductModel.builder ()
                .id ( product.getId() )
                .name( product.getName() )
                .description( product.getDescription() )
                .image( product.getImage() )
                .price( product.getPrice() )
                .discount( product.getDiscount() )
                .specialPrice( product.getSpecialPrice() )
                .quantity ( product.getQuantity() )
                .category ( product.getCategory() )
                .build();
    };

    public static final BiFunction < Product, ProductModel, Product> toUpdatedEntity = ( product, productModel) -> {
        product.setName( productModel.getName() );
        product.setDescription( productModel.getDescription() );
        product.setImage( productModel.getImage() );
        product.setPrice( productModel.getPrice() );
        product.setDiscount( productModel.getDiscount() );
        product.setSpecialPrice( productModel.getSpecialPrice() );
        product.setQuantity( productModel.getQuantity() );
        product.setCategory( productModel.getCategory() );
        return product;
    };
}
