package com.rkecom.core.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rkecom.json.util.annotation.JsonExcludeNullAndEmpty;
import com.rkecom.json.util.serializer.MapEntryValueSerializer;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<M> {
    private List < M > content;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private Boolean isFirstPage;
    private Boolean isLastPage;

    @JsonSerialize(using = MapEntryValueSerializer.class)
    @JsonExcludeNullAndEmpty(excludeNull = true)
    private Map <String, String> links;
}
