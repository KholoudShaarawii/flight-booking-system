package com.flightbooking.common.response;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PageResponseTest {
    @Test
    void pageResponse_WithPaginationData_ShouldCreatePageResponse() {

        List<String> content = List.of("Flight 1", "Flight 2");
        int page = 0;
        int size = 10;
        long totalElements = 22;
        int totalPages = 3;


        PageResponse<String> response = PageResponse.of(content, page, size, totalElements, totalPages);

        assertAll(
                () -> assertEquals(content, response.getContent()),
                () -> assertEquals(page, response.getPage()),
                () -> assertEquals(size, response.getSize()),
                () -> assertEquals(totalElements, response.getTotalElements()),
                () -> assertEquals(totalPages, response.getTotalPages())
        );
    }
}
