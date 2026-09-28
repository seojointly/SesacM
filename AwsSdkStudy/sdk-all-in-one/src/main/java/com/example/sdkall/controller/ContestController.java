package com.example.sdkall.controller;

import com.example.sdkall.dto.ContestRequest;
import com.example.sdkall.dto.ContestResponse;
import com.example.sdkall.service.ContestService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/contests")
public class ContestController {

    private final ContestService contestService;

    public ContestController(ContestService contestService) {
        this.contestService = contestService;
    }

    // 공모전 접수를 등록
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String submit(
            @RequestPart("data") ContestRequest request,
            @RequestPart("photo") MultipartFile photo) {

        // Service에서 전체 접수 흐름 처리
        return contestService.submit(request, photo);
    }

    // 접수번호로 접수내역을 조회
    @GetMapping("/{entryId}")
    public ContestResponse findById(@PathVariable String entryId) {

        // Service에서 DynamoDB 조회와 Presigned URL 생성을 처리
        return contestService.findById(entryId);
    }
}