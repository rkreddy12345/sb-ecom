package com.rkecom.core.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.rkecom.json.util.annotation.JsonConditionalInclude;
import com.rkecom.json.util.serializer.MapEntryValueSerializer;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * A generic API response model for paginated data.
 *
 * Annotations are used to control the JSON serialization behavior for fields:
 * <br>- {@code @JsonInclude(JsonInclude.Include.NON_NULL)}: Excludes fields with {@code null} values from JSON output.
 * <br>- {@code @JsonSerialize(using = MapEntryValueSerializer.class)}: Applies a custom serializer for selective exclusion of null or empty map values.
 * <br>- {@code @JsonConditionalInclude}: Configures the behavior of the custom serializer to exclude {@code null} and/or empty values based on its attributes.
 *
 * <br>The combination of these annotations ensures efficient and consistent JSON output while maintaining flexibility for customization.
 */
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

    /**
     * Custom serialization applied to the `links` map:
     * - Excludes null or empty values as configured by {@code @JsonConditionalInclude}.
     * - Uses {@code MapEntryValueSerializer} to define custom rules for exclusion.
     */
    @JsonSerialize(using = MapEntryValueSerializer.class)
    @JsonConditionalInclude(excludeNull = true)
    private Map <String, String> links;
}
