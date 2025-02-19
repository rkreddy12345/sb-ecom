package com.rkecom.core.util;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

public class PaginationUtil {
    private PaginationUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "30";

    public static final String SORT_IN_ASC = "asc";
    public static final String SORT_IN_DESC = "desc";

    public static Map <String, String> generatePaginationLinks( int currentPage, int totalPages ) {
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentRequestUri ().replaceQuery ( null ).toUriString ();
        Map<String, String> links = new LinkedHashMap <> ();
        links.put("first", baseUrl+"?page=0");
        links.put("previous", currentPage>0 ? baseUrl+"?page="+(currentPage-1) : null);
        links.put("current", baseUrl+"?page="+currentPage);
        links.put("next", currentPage<totalPages-1 ? baseUrl+"?page="+(currentPage+1) : null);
        links.put("last", baseUrl+"?page="+(totalPages-1));
        return links;
    }
}
