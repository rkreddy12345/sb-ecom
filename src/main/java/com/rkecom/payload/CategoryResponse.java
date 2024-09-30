package com.rkecom.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rkecom.ui.model.CategoryModel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryResponse {
    private List < CategoryModel > content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean isFirstPage;
    private boolean isLastPage;
}
