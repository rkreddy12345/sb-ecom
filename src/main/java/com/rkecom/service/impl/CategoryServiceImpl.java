package com.rkecom.service.impl;

import com.rkecom.config.AppConstants;
import com.rkecom.db.entity.Category;
import com.rkecom.exception.APIException;
import com.rkecom.exception.ResourceNotFoundException;
import com.rkecom.objects.mapper.CategoryMapper;
import com.rkecom.response.CategoryResponse;
import com.rkecom.repository.CategoryRepository;
import com.rkecom.service.CategoryService;
import com.rkecom.ui.model.CategoryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Override
    public CategoryModel createCategory ( CategoryModel categoryModel ) {
        Category category = CategoryMapper.toEntity.apply ( categoryModel );
        Optional<Category> optionalCategory = categoryRepository.findByName(categoryModel.getName());
        if(optionalCategory.isPresent()) {
            throw new APIException ( "category already exists with name-" + categoryModel.getName() );
        }else{
            Category savedCategory = categoryRepository.save(category);
            return CategoryMapper.toModel.apply ( savedCategory );
        }
    }

    @Override
    public CategoryResponse getAllCategories ( Integer page, Integer size, String sortBy, String sortOrder ) {
        Sort sort=sortOrder.equalsIgnoreCase ( AppConstants.SORT_IN_ASC )
                ? Sort.by ( sortBy ).ascending ()
                : Sort.by ( sortBy ).descending ();
       Page<Category> categoryPage = categoryRepository.findAll ( PageRequest.of ( page, size , sort ) );
       List<Category> categories = categoryPage.getContent ();
       if(categories.isEmpty()) {
           throw new APIException ( "No categories found" );
       }else{
           List<CategoryModel> categoryModels = categories.stream().map (
                   category -> CategoryMapper.toModel.apply ( category )
           ).toList ();

            return buildCategoryResponse ( categoryModels, categoryPage );
       }
    }

    private static CategoryResponse buildCategoryResponse ( List < CategoryModel > categoryModels, Page < Category > categoryPage ) {
        return CategoryResponse.builder ()
                .content ( categoryModels )
                .pageNumber ( categoryPage.getNumber () )
                .pageSize ( categoryPage.getSize () )
                .totalElements ( categoryPage.getTotalElements () )
                .totalPages ( categoryPage.getTotalPages () )
                .isFirstPage ( categoryPage.isFirst () )
                .isLastPage ( categoryPage.isLast () )
                .build();
    }

    @Override
    public CategoryModel updateCategory ( CategoryModel categoryModel, Long id ) {
        Category category=CategoryMapper.toEntity.apply ( categoryModel );
        Category categoryToUpdate = categoryRepository.findById ( id )
                .orElseThrow (()->new ResourceNotFoundException ("category", "id", id));
        categoryToUpdate.setName ( category.getName() );
        Category updatedCategory=categoryRepository.save(categoryToUpdate);
        return CategoryMapper.toModel.apply ( updatedCategory );
    }

    @Override
    public CategoryModel deleteCategoryById ( Long id ) {
        Category category = categoryRepository.findById ( id )
                .orElseThrow (()->new ResourceNotFoundException ("category", "id", id));
        categoryRepository.delete (category);
        return CategoryMapper.toModel.apply ( category );
    }
}
