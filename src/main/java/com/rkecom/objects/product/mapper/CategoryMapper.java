package com.rkecom.objects.product.mapper;

import com.rkecom.core.util.MapperUtil;
import com.rkecom.db.entity.product.Category;
import com.rkecom.ui.model.product.CategoryModel;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;
import java.util.function.Function;

@Component
public class CategoryMapper {

    //model to entity
    public final Function<CategoryModel, Category> toEntity(){
        return  categoryModel-> Category.builder ()
                .name ( categoryModel.getName () ).build ();
    }
    //entity to model
    public final Function<Category, CategoryModel> toModel(){
        return category -> CategoryModel.builder ()
                .categoryId ( category.getCategoryId () )
                .name ( category.getName () )
                .build ();
    }

    public final BiFunction<Category, CategoryModel, Category> toUpdatedEntity(){
        return (category, categoryModel)-> {
            MapperUtil.updateField ( category.getName (), categoryModel.getName (), category::setName);
            return category;
        };
    }
}
