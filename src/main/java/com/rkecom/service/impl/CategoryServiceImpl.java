package com.rkecom.service.impl;

import com.rkecom.core.exception.ApiException;
import com.rkecom.core.exception.ResourceNotFoundException;
import com.rkecom.core.response.ApiResponse;
import com.rkecom.core.response.util.ApiResponseUtil;
import com.rkecom.core.util.Pagination;
import com.rkecom.data.repository.CategoryRepository;
import com.rkecom.db.entity.Category;
import com.rkecom.objects.mapper.CategoryMapper;
import com.rkecom.service.CategoryService;
import com.rkecom.ui.model.CategoryModel;
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
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Override
    @Transactional
    public CategoryModel createCategory ( CategoryModel categoryModel ) {
        Category category = CategoryMapper.toEntity.apply ( categoryModel );
        Optional<Category> optionalCategory = categoryRepository.findByName(categoryModel.getName());
        if(optionalCategory.isPresent()) {
            throw new ApiException ( "category already exists with name-" + categoryModel.getName() );
        }else{
            Category savedCategory = categoryRepository.save(category);
            return CategoryMapper.toModel.apply ( savedCategory );
        }
    }

    @Override
    public ApiResponse<CategoryModel> getAllCategories ( Integer page, Integer size, String sortBy, String sortOrder ) {
        Sort sort=sortOrder.equalsIgnoreCase ( Pagination.SORT_IN_ASC )
                ? Sort.by ( sortBy ).ascending ()
                : Sort.by ( sortBy ).descending ();
       Page<Category> categoryPage = categoryRepository.findAll ( PageRequest.of ( page, size , sort ) );
        return getCategoryResponse ( categoryPage );
    }

    @Override
    @Transactional
    public CategoryModel updateCategory ( CategoryModel categoryModel, Long id ) {
        Category existingCategory = categoryRepository.findById ( id )
                .orElseThrow (()->new ResourceNotFoundException ("category", "id", id));
        Category upadatedCategory=CategoryMapper.toUpdatedEntity.apply ( existingCategory , categoryModel );
        return CategoryMapper.toModel.apply ( categoryRepository.save(upadatedCategory ) );
    }

    @Override
    @Transactional
    public CategoryModel deleteCategoryById ( Long id ) {
        Category category = categoryRepository.findById ( id )
                .orElseThrow (()->new ResourceNotFoundException ("category", "id", id));
        categoryRepository.delete (category);
        return CategoryMapper.toModel.apply ( category );
    }

    @Override
    public ApiResponse < CategoryModel > getAllCategories ( Integer page, Integer size ) {
        Page<Category> categoryPage = categoryRepository.findAll ( PageRequest.of ( page, size ) );
        return getCategoryResponse ( categoryPage );
    }

    private static ApiResponse < CategoryModel > getCategoryResponse ( Page < Category > categoryPage ) {
        List <Category> categories = categoryPage.getContent ();
        if(categories.isEmpty()) {
            throw new ApiException ( "No categories found" );
        }else{
            List<CategoryModel> categoryModels = categories.stream()
                    .map ( CategoryMapper.toModel )
                    .toList ();

            return ApiResponseUtil.buildApiResponse ( categoryModels, categoryPage );
        }
    }
}
