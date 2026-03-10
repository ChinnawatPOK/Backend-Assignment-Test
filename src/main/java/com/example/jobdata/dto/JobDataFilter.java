package com.example.jobdata.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class JobDataFilter {
    private String jobTitle;
    private String gender;
    private BigDecimal salaryGte;
    private BigDecimal salaryLte;
    private BigDecimal salaryEq;
    private List<String> fields;
    private String sort;
    private String sortType;
}
