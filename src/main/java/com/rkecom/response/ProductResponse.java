package com.rkecom.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rkecom.ui.model.ProductModel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse {
    private List < ProductModel > content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean isFirstPage;
    private boolean isLastPage;
}
