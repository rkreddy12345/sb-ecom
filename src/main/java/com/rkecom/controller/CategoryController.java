package com.rkecom.controller;

import com.rkecom.core.response.ApiResponse;
import com.rkecom.core.util.Pagination;
import com.rkecom.crud.service.CategoryService;
import com.rkecom.ui.model.CategoryModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1")
@RestController
@Validated
public class CategoryController {

    private final CategoryService categoryService;
    @Autowired
    public CategoryController ( CategoryService categoryService ) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/categories/sorted")
    public ResponseEntity< ApiResponse<CategoryModel> > getAllCategories(
            @RequestParam(defaultValue = Pagination.PAGE_NUMBER) Integer page,
            @RequestParam(defaultValue = Pagination.PAGE_SIZE) Integer size,
            @RequestParam(defaultValue = Pagination.SORT_BY_ID) String sortBy,
            @RequestParam(defaultValue = Pagination.SORT_IN_ASC) String sortOrder) {
        return ResponseEntity.ok (categoryService.getAllCategories (page, size, sortBy, sortOrder));
    }

    @GetMapping("/public/categories")
    public ResponseEntity< ApiResponse<CategoryModel> > getAllCategories(
            @RequestParam(defaultValue = Pagination.PAGE_NUMBER) Integer page,
            @RequestParam(defaultValue = Pagination.PAGE_SIZE) Integer size) {
        return ResponseEntity.ok (categoryService.getAllCategories (page, size));
    }

    @PostMapping("/admin/categories")
    public ResponseEntity< CategoryModel > createCategory( @Valid @RequestBody CategoryModel categoryDTO) {
       return new ResponseEntity <> ( categoryService.createCategory ( categoryDTO ), HttpStatus.CREATED );
    }

    @PutMapping("/admin/categories/{id}")
    public ResponseEntity< CategoryModel > updateCategory( @Valid @RequestBody CategoryModel categoryDTO, @PathVariable @Positive Long id) {
        return ResponseEntity.ok (categoryService.updateCategory ( categoryDTO , id ));
    }

    @DeleteMapping("/admin/categories/{id}")
    public ResponseEntity< CategoryModel > deleteCategory ( @PathVariable Long id) {
        return ResponseEntity.status ( HttpStatus.OK ).body ( categoryService.deleteCategoryById ( id ) );
    }
}
