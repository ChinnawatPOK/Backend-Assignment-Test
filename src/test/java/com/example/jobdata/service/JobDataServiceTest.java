package com.example.jobdata.service;

import com.example.jobdata.dto.ApiResponse;
import com.example.jobdata.dto.JobDataFilter;
import com.example.jobdata.model.JobData;
import com.example.jobdata.repository.JobDataRepository;
import com.example.jobdata.util.FieldExtractor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JobDataServiceTest {

    @Mock
    private JobDataRepository repository;

    @Mock
    private FieldExtractor fieldExtractor;

    @InjectMocks
    private JobDataService service;

    private List<JobData> mockData;

    @BeforeEach
    void setUp() {
        mockData = List.of(
                JobData.builder()
                        .jobTitle("Software Engineer")
                        .gender("Male")
                        .salary(new BigDecimal("120000"))
                        .employer("Google")
                        .build(),
                JobData.builder()
                        .jobTitle("Senior Software Engineer")
                        .gender("Female")
                        .salary(new BigDecimal("150000"))
                        .employer("Amazon")
                        .build(),
                JobData.builder()
                        .jobTitle("Data Analyst")
                        .gender("Female")
                        .salary(new BigDecimal("80000"))
                        .employer("Meta")
                        .build(),
                JobData.builder()
                        .jobTitle("DevOps Engineer")
                        .gender("Male")
                        .salary(null)
                        .employer("Netflix")
                        .build()
        );

        when(repository.findAll()).thenReturn(mockData);
    }

    // Filter: job_title

    @Test
    void filter_byJobTitle_shouldReturnMatchingRecords() {
        JobDataFilter filter = new JobDataFilter();
        filter.setJobTitle("Engineer");

        ApiResponse response = service.query(filter);

        assertThat(response.getTotal()).isEqualTo(3);
    }

    @Test
    void filter_byJobTitle_caseInsensitive_shouldMatch() {
        JobDataFilter filter = new JobDataFilter();
        filter.setJobTitle("software engineer");

        ApiResponse response = service.query(filter);

        assertThat(response.getTotal()).isEqualTo(2);
    }

    // Filter: gender

    @Test
    void filter_byGender_shouldReturnMatchingRecords() {
        JobDataFilter filter = new JobDataFilter();
        filter.setGender("Female");

        ApiResponse response = service.query(filter);

        assertThat(response.getTotal()).isEqualTo(2);
    }

    // Filter: salary

    @Test
    void filter_bySalaryGte_shouldReturnRecordsAboveThreshold() {
        JobDataFilter filter = new JobDataFilter();
        filter.setSalaryGte(new BigDecimal("120000"));

        ApiResponse response = service.query(filter);

        // 120000 and 150000 pass, null not pass
        assertThat(response.getTotal()).isEqualTo(2);
    }

    @Test
    void filter_bySalaryLte_shouldReturnRecordsBelowThreshold() {
        JobDataFilter filter = new JobDataFilter();
        filter.setSalaryLte(new BigDecimal("120000"));

        ApiResponse response = service.query(filter);

        // 80000 and 120000 pass, null not pass
        assertThat(response.getTotal()).isEqualTo(2);
    }

    @Test
    void filter_bySalaryNull_shouldBeExcluded() {
        JobDataFilter filter = new JobDataFilter();
        filter.setSalaryGte(new BigDecimal("1"));

        ApiResponse response = service.query(filter);

        // DevOps Engineer salary = null was filter out
        assertThat(response.getTotal()).isEqualTo(3);
    }

    // Sort

    @Test
    void sort_bySalaryAsc_shouldReturnInAscendingOrder() {
        JobDataFilter filter = new JobDataFilter();
        filter.setSort("salary");
        filter.setSortType("ASC");

        ApiResponse response = service.query(filter);

        List<JobData> data = (List<JobData>) response.getData();
        assertThat(data.get(0).getSalary()).isNull();
        assertThat(data.get(1).getSalary()).isEqualByComparingTo("80000");
        assertThat(data.get(2).getSalary()).isEqualByComparingTo("120000");
        assertThat(data.get(3).getSalary()).isEqualByComparingTo("150000");
    }

    @Test
    void sort_bySalaryDesc_shouldReturnInDescendingOrder() {
        JobDataFilter filter = new JobDataFilter();
        filter.setSort("salary");
        filter.setSortType("DESC");

        ApiResponse response = service.query(filter);

        List<JobData> data = (List<JobData>) response.getData();
        assertThat(data.get(0).getSalary()).isEqualByComparingTo("150000");
        assertThat(data.get(1).getSalary()).isEqualByComparingTo("120000");
    }

    // No filter

    @Test
    void noFilter_shouldReturnAllRecords() {
        ApiResponse response = service.query(new JobDataFilter());

        assertThat(response.getTotal()).isEqualTo(4);
    }
}