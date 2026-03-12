package com.example.jobdata.controller;

import com.example.jobdata.dto.ApiResponse;
import com.example.jobdata.service.JobDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(JobDataController.class)
class JobDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JobDataService service;

    @Test
    void getJobData_noParams_shouldReturnOk() throws Exception {
        when(service.query(any())).thenReturn(ApiResponse.builder().total(0).data(List.of()).build());

        mockMvc.perform(get("/api/v1/job_data"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(0));
    }

    @Test
    void getJobData_withSalaryGte_shouldPassFilterToService() throws Exception {
        when(service.query(any())).thenReturn(ApiResponse.builder().total(1).data(List.of()).build());

        mockMvc.perform(get("/api/v1/job_data")
                        .param("salaryGte", "120000"))
                .andExpect(status().isOk());

        verify(service).query(argThat(filter ->
                filter.getSalaryGte().compareTo(new BigDecimal("120000")) == 0
        ));
    }

    @Test
    void getJobData_withJobTitle_shouldPassFilterToService() throws Exception {
        when(service.query(any())).thenReturn(ApiResponse.builder().total(0).data(List.of()).build());

        mockMvc.perform(get("/api/v1/job_data")
                        .param("job_title", "Software Engineer"))
                .andExpect(status().isOk());

        verify(service).query(argThat(filter ->
                "Software Engineer".equals(filter.getJobTitle())
        ));
    }

    @Test
    void getJobData_withGender_shouldPassFilterToService() throws Exception {
        when(service.query(any())).thenReturn(ApiResponse.builder().total(0).data(List.of()).build());

        mockMvc.perform(get("/api/v1/job_data")
                        .param("gender", "Female"))
                .andExpect(status().isOk());

        verify(service).query(argThat(filter ->
                "Female".equals(filter.getGender())
        ));
    }

    @Test
    void getJobData_withFields_shouldParseToList() throws Exception {
        when(service.query(any())).thenReturn(ApiResponse.builder().total(0).build());

        mockMvc.perform(get("/api/v1/job_data")
                        .param("fields", "job_title,salary,gender"))
                .andExpect(status().isOk());

        verify(service).query(argThat(filter ->
                filter.getFields() != null &&
                        filter.getFields().containsAll(List.of("job_title", "salary", "gender"))
        ));
    }

    @Test
    void getJobData_withSortDesc_shouldPassSortToService() throws Exception {
        when(service.query(any())).thenReturn(ApiResponse.builder().total(0).data(List.of()).build());

        mockMvc.perform(get("/api/v1/job_data")
                        .param("sort", "salary")
                        .param("sort_type", "DESC"))
                .andExpect(status().isOk());

        verify(service).query(argThat(filter ->
                "salary".equals(filter.getSort()) &&
                        "DESC".equals(filter.getSortType())
        ));
    }

    @Test
    void getJobData_defaultSortType_shouldBeASC() throws Exception {
        when(service.query(any())).thenReturn(ApiResponse.builder().total(0).data(List.of()).build());

        mockMvc.perform(get("/api/v1/job_data"))
                .andExpect(status().isOk());

        verify(service).query(argThat(filter ->
                "ASC".equals(filter.getSortType())
        ));
    }
}