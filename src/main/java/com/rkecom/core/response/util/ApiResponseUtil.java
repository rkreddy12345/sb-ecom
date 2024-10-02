package com.rkecom.core.response.util;

import com.rkecom.core.response.ApiResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public class ApiResponseUtil {

    public static < M, E > ApiResponse<M> buildApiResponse( List<M> content , Page<E> page) {
        return ApiResponse.<M>builder ()
                .content ( content )
                .pageNumber ( page.getNumber () )
                .pageSize ( page.getSize () )
                .totalElements ( page.getTotalElements () )
                .totalPages ( page.getTotalPages () )
                .isFirstPage ( page.isFirst () )
                .isLastPage ( page.isLast () )
                .build ();
    }

}
