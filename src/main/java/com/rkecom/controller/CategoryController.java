package com.rkecom.controller;

import com.rkecom.config.AppConstants;
import com.rkecom.ui.model.CategoryModel;
import com.rkecom.core.response.ApiResponse;
import com.rkecom.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1")
@RestController
public class CategoryController {

    private CategoryService categoryService;
    @Autowired
    public CategoryController ( CategoryService categoryService ) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/categories")
    public ResponseEntity< ApiResponse<CategoryModel> > getAllCategories(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER) Integer page,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE) Integer size,
            @RequestParam(defaultValue = AppConstants.SORT_CATEGORIES_BY_ID) String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_IN_ASC) String sortOrder) {
        return ResponseEntity.ok (categoryService.getAllCategories (page, size, sortBy, sortOrder));
    }

    @PostMapping("/admin/categories")
    public ResponseEntity< CategoryModel > createCategory( @Valid @RequestBody CategoryModel categoryDTO) {
       return new ResponseEntity <> ( categoryService.createCategory ( categoryDTO ), HttpStatus.CREATED );
    }

    @PutMapping("/admin/categories/{id}")
    public ResponseEntity< CategoryModel > updateCategory( @Valid @RequestBody CategoryModel categoryDTO, @PathVariable Long id) {
        return ResponseEntity.ok (categoryService.updateCategory ( categoryDTO , id ));
    }

    @DeleteMapping("/admin/categories/{id}")
    public ResponseEntity< CategoryModel > deleteCategory ( @PathVariable Long id) {
        return ResponseEntity.status ( HttpStatus.OK ).body ( categoryService.deleteCategoryById ( id ) );
    }
}
