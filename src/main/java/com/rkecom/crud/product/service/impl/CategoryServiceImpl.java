package com.rkecom.crud.product.service.impl;

import com.rkecom.core.exception.ApiException;
import com.rkecom.core.exception.ResourceNotFoundException;
import com.rkecom.core.response.ApiResponse;
import com.rkecom.core.response.util.ApiResponseUtil;
import com.rkecom.core.util.PaginationUtil;
import com.rkecom.core.util.ResourceConstants;
import com.rkecom.crud.product.service.CategoryService;
import com.rkecom.data.product.repository.CategoryRepository;
import com.rkecom.db.entity.product.Category;
import com.rkecom.objects.product.mapper.CategoryMapper;
import com.rkecom.web.product.model.CategoryModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor (onConstructor_ = {@Autowired})
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    @Transactional
    public CategoryModel createCategory ( CategoryModel categoryModel ) {
        if(isCategoryExistsWithName ( categoryModel.getName(), null )){
            throw new ApiException ( "category already exists with name : " + categoryModel.getName() );
        }
        Category category = categoryMapper.toEntity ().apply ( categoryModel );
        return categoryMapper.toModel().apply ( categoryRepository.save ( category ) );
    }

    @Override
    public ApiResponse<CategoryModel> getAllCategories ( Integer page, Integer size, String sortBy, String sortOrder ) {
        Sort sort=sortOrder.equalsIgnoreCase ( PaginationUtil.SORT_IN_ASC )
                ? Sort.by ( sortBy ).ascending ()
                : Sort.by ( sortBy ).descending ();
       Page<Category> categoryPage = categoryRepository.findAll ( PageRequest.of ( page, size , sort ) );
        return buildCategoryApiResponse ( categoryPage );
    }

    @Override
    @Transactional
    public CategoryModel updateCategory ( CategoryModel categoryModel, Long id ) {
        Category existingCategory = categoryRepository.findById ( id )
                .orElseThrow (()->new ResourceNotFoundException ( ResourceConstants.CATEGORY, "id", id));
        if(isCategoryExistsWithName ( categoryModel.getName(), id )){
            throw new ApiException ( "category already exists with name - " + categoryModel.getName() );
        }
        Category toUpdatedEntity = categoryMapper.toUpdatedEntity().apply ( existingCategory, categoryModel );
        return categoryMapper.toModel().apply ( categoryRepository.save(toUpdatedEntity ) );
    }

    @Override
    @Transactional
    public CategoryModel deleteCategoryById ( Long id ) {
        Category category = categoryRepository.findById ( id )
                .orElseThrow (()->new ResourceNotFoundException (ResourceConstants.CATEGORY, "id", id));
        categoryRepository.delete (category);
        return categoryMapper.toModel().apply ( category );
    }

    @Override
    public ApiResponse < CategoryModel > getAllCategories ( Integer page, Integer size ) {
        Page<Category> categoryPage = categoryRepository.findAll ( PageRequest.of ( page, size ) );
        return buildCategoryApiResponse ( categoryPage );
    }

    private ApiResponse < CategoryModel > buildCategoryApiResponse ( Page < Category > categoryPage ) {
        List <Category> categories = categoryPage.getContent ();
        if(categories.isEmpty()) {
            throw new ApiException ( "No categories found" );
        }else{
            List<CategoryModel> categoryModels = categories.stream()
                    .map ( categoryMapper.toModel() )
                    .toList ();

            return ApiResponseUtil.buildApiResponse ( categoryModels, categoryPage );
        }
    }

    private boolean isCategoryExistsWithName(String name, Long id){
        Optional<Category> category = categoryRepository.findByName(name);
        return category.isPresent() && (id==null || !category.get ().getCategoryId ().equals ( id ));
    }

}
