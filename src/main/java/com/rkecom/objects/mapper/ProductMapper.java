package com.rkecom.objects.mapper;

import com.rkecom.db.entity.Product;
import com.rkecom.ui.model.ProductModel;

import java.util.function.Function;

public class ProductMapper {
    public static final Function< ProductModel, Product > toEntity = productModel -> {
        return Product.builder()
                .name(productModel.getName())
                .description ( productModel.getDescription() )
                .image(productModel.getImage())
                .price(productModel.getPrice())
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
                .specialPrice( product.getSpecialPrice() )
                .quantity ( product.getQuantity() )
                .category ( product.getCategory() )
                .build();
    };
}
