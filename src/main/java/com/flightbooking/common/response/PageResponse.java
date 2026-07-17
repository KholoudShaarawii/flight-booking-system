package com.flightbooking.common.response;

import lombok.Getter;

import java.util.List;


@Getter
public final class PageResponse<T> { //ApiResponse<PageResponse<classResponse>>
    private final List<T> content;
    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;

    private PageResponse(
            List<T> content,
            int page,
            int size,
            long totalElements,
            int totalPages ) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public static <T> PageResponse<T> of(List<T> content,
                                         int page,
                                         int size,
                                         long totalElements,
                                         int totalPages) {
        return new PageResponse<>(
                content,
                page,
                size,
                totalElements,
                totalPages );
    }
}
/*{
  "success": true,
  "message": "Customers retrieved successfully",
  "data": {
    "content": [],
    "page": 0,
    "size": 10,
    "totalElements": 100,
    "totalPages": 10
  }
}
*/