package com.rkecom.core.util;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

public class Pagination {
    private Pagination() {
        super();
    }
    public static final String PAGE_NUMBER = "0";
    public static final String PAGE_SIZE = "30";
    public static final String SORT_BY_ID = "id";
    public static final String SORT_BY_NAME = "name";
    public static final String SORT_IN_ASC = "asc";
    public static final String SORT_IN_DESC = "desc";

    public static Map <String, String> generatePaginationLinks( int currentPage, int totalPages ) {
        final String baseUrl = ServletUriComponentsBuilder.fromCurrentRequestUri ().replaceQuery ( null ).toUriString ();
        Map<String, String> links = new HashMap <> ();
        links.put("first", baseUrl+"?page=0");
        links.put("previous", currentPage>0?baseUrl+"?page="+(currentPage-1):null);
        links.put("current", baseUrl+"?page="+currentPage);
        links.put("next", currentPage<totalPages-1?baseUrl+"?page="+(currentPage+1):null);
        links.put("last", baseUrl+"?page="+(totalPages-1));
        return links;
    }
}
