package com.example.jobdata.dto;

import com.example.jobdata.model.JobData;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    private int total;
    private List<JobData> data;
    private List<Map<String, Object>> sparseData;
}
