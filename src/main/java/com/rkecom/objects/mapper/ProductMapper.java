package com.rkecom.objects.mapper;

import com.rkecom.core.util.MapperUtil;
import com.rkecom.db.entity.product.Product;
import com.rkecom.ui.model.product.ProductModel;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;
import java.util.function.Function;

@Component
public class ProductMapper {

    public final Function< ProductModel, Product > toEntity(){
       return productModel -> Product.builder()
                    .name(productModel.getName())
                    .description ( productModel.getDescription() )
                    .image(productModel.getImage())
                    .price(productModel.getPrice())
                    .discount ( productModel.getDiscount() )
                    .specialPrice ( productModel.getSpecialPrice() )
                    .quantity ( productModel.getQuantity() )
                    .category ( productModel.getCategory() )
                    .build();
    }

    public final Function<Product, ProductModel> toModel(){
        return product -> ProductModel.builder ()
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
    }

    public final BiFunction<Product, ProductModel, Product> toUpdatedEntity(){
        return ( product, productModel ) -> {
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



}
