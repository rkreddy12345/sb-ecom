package com.rkecom.objects.mapper;

import com.rkecom.db.entity.Category;
import com.rkecom.ui.model.CategoryModel;

import java.util.function.Function;

public class CategoryMapper {

    //model to entity
    public static final Function<CategoryModel, Category> toEntity = categoryModel-> Category.builder ()
            .name ( categoryModel.getName () ).build ();
    //entity to model
    public static final Function<Category, CategoryModel> toModel = category -> CategoryModel.builder ()
            .id( category.getId () )
            .name ( category.getName () )
            .build ();
}
