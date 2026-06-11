package com.example.mybatis.dto;

import java.util.List;

public record PageResponse<T>(
    List<T> contents,
    int page,
    int size,
    int totalPages,
    long totalElements, // post번호는 long으로 해놨기 때문에 long으로 설정
    String sort) { }
