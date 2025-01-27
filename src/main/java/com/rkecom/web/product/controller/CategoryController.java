package com.rkecom.web.product.controller;

import com.rkecom.core.controller.BaseController;
import com.rkecom.core.response.ApiResponse;
import com.rkecom.core.util.PaginationUtil;
import com.rkecom.crud.product.service.CategoryService;
import com.rkecom.web.product.model.CategoryModel;
import com.rkecom.web.product.constants.CategoryConstants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1")
@RestController
@Validated
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CategoryController extends BaseController {

    private final CategoryService categoryService;

    @GetMapping("/public/categories/sorted")
    public ResponseEntity< ApiResponse<CategoryModel> > getAllCategories(
            @RequestParam(defaultValue = PaginationUtil.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(defaultValue = PaginationUtil.DEFAULT_PAGE_SIZE) Integer size,
            @RequestParam(defaultValue = CategoryConstants.SORT_BY_ID ) String sortBy,
            @RequestParam(defaultValue = PaginationUtil.SORT_IN_ASC) String sortOrder) {
        return ResponseEntity.ok (categoryService.getAllCategories (page, size, sortBy, sortOrder));
    }

    @GetMapping("/public/categories")
    public ResponseEntity< ApiResponse<CategoryModel> > getAllCategories(
            @RequestParam(defaultValue = PaginationUtil.DEFAULT_PAGE_NUMBER) Integer page,
            @RequestParam(defaultValue = PaginationUtil.DEFAULT_PAGE_SIZE) Integer size) {
        return ResponseEntity.ok (categoryService.getAllCategories (page, size));
    }

    @PostMapping("/admin/categories")
    public ResponseEntity< CategoryModel > createCategory( @Valid @RequestBody CategoryModel categoryModel) {
       return new ResponseEntity <> ( categoryService.createCategory ( categoryModel ), HttpStatus.CREATED );
    }

    @PutMapping("/admin/categories/{id}")
    public ResponseEntity< CategoryModel > updateCategory( @Valid @RequestBody CategoryModel categoryModel, @PathVariable @Positive Long id) {
        return ResponseEntity.ok (categoryService.updateCategory ( categoryModel , id ));
    }

    @DeleteMapping("/admin/categories/{id}")
    public ResponseEntity< CategoryModel > deleteCategory ( @PathVariable Long id) {
        return ResponseEntity.status ( HttpStatus.OK ).body ( categoryService.deleteCategoryById ( id ) );
    }
}
