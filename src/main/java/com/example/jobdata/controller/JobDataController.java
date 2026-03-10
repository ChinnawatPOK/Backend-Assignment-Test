package com.example.jobdata.controller;

import com.example.jobdata.dto.ApiResponse;
import com.example.jobdata.dto.JobDataFilter;
import com.example.jobdata.service.JobDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Arrays;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class JobDataController {

    private final JobDataService service;

    @GetMapping("/job_data")
    public ResponseEntity<ApiResponse> getJobData(
            @RequestParam(required = false) String job_title,
            @RequestParam(required = false) String gender,
            @RequestParam(name = "salary[gte]", required = false) BigDecimal salaryGte,
            @RequestParam(name = "salary[lte]", required = false) BigDecimal salaryLte,
            @RequestParam(name = "salary[eq]",  required = false) BigDecimal salaryEq,
            @RequestParam(required = false) String fields,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false, defaultValue = "ASC") String sort_type
    ) {

        JobDataFilter filter = JobDataFilter.builder()
                .jobTitle(job_title)
                .gender(gender)
                .salaryGte(salaryGte)
                .salaryLte(salaryLte)
                .salaryEq(salaryEq)
                .sort(sort)
                .sortType(sort_type)
                .build();

        if (fields != null && !fields.isBlank()) {
            filter.setFields(Arrays.asList(fields.split(",")));
        }

        return ResponseEntity.ok(service.query(filter));
    }

}
