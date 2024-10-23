package com.rkecom.objects.mapper;

import com.rkecom.core.util.MapperUtil;
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

    public static final BiFunction<Product, ProductModel, Product> toUpdatedEntity = ( product, productModel ) -> {
        MapperUtil.updateField ( product.getName (), productModel.getName (), product::setName );
        MapperUtil.updateField ( product.getDescription (), productModel.getDescription (), product::setDescription );
        MapperUtil.updateField ( product.getImage (), productModel.getImage (), product::setImage );
        MapperUtil.updateField ( product.getPrice (), productModel.getPrice (), product::setPrice );
        MapperUtil.updateField ( product.getDiscount (), productModel.getDiscount (), product::setDiscount );
        MapperUtil.updateField ( product.getSpecialPrice (), productModel.getSpecialPrice (), product::setSpecialPrice );
        MapperUtil.updateField ( product.getQuantity (), productModel.getQuantity (), product::setQuantity );
        MapperUtil.updateField ( product.getCategory (), productModel.getCategory (), product::setCategory );
        return product;
    };



}
