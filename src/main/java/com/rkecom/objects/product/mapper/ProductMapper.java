package com.rkecom.objects.product.mapper;

import com.rkecom.core.util.MapperUtil;
import com.rkecom.db.entity.product.Product;
import com.rkecom.web.product.model.ProductModel;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;
import java.util.function.Function;

@Component
public class ProductMapper {

    public final Function<ProductModel, Product> toEntity() {
        return productModel -> {
            Product product = Product.builder()
                    .name(productModel.getName())
                    .description(productModel.getDescription())
                    .image(productModel.getImage())
                    .price(productModel.getPrice())
                    .discount(productModel.getDiscount())
                    .quantity(productModel.getQuantity())
                    .build();

            // Ensure special price is set based on price and discount
            product.updateSpecialPrice();

            return product;
        };
    }


    public final Function<Product, ProductModel> toModel(){
        return product -> ProductModel.builder ()
                .productId ( product.getProductId () )
                .name( product.getName() )
                .description( product.getDescription() )
                .image( product.getImage() )
                .price( product.getPrice() )
                .discount( product.getDiscount() )
                .specialPrice( product.getSpecialPrice() )
                .quantity ( product.getQuantity() )
                .categoryId ( product.getCategory ().getCategoryId () )
                .build();
    }

    public final BiFunction<Product, ProductModel, Product> toUpdatedEntity(){
        return ( product, productModel ) -> {
            MapperUtil.updateField ( product.getName (), productModel.getName (), product::setName );
            MapperUtil.updateField ( product.getDescription (), productModel.getDescription (), product::setDescription );
            MapperUtil.updateField ( product.getImage (), productModel.getImage (), product::setImage );
            MapperUtil.updateField ( product.getPrice (), productModel.getPrice (), product::setPrice );
            MapperUtil.updateField ( product.getDiscount (), productModel.getDiscount (), product::setDiscount );
            MapperUtil.updateField ( product.getQuantity (), productModel.getQuantity (), product::setQuantity );
            product.updateSpecialPrice ();
            return product;
        };
    }



}
