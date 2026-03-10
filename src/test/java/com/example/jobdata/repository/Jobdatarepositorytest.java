package com.example.jobdata.repository;

import com.example.jobdata.model.JobData;
import com.example.jobdata.util.SalaryParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class JobDataRepositoryTest {

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();

    @Spy
    private SalaryParser salaryParser = new SalaryParser();

    @InjectMocks
    private JobDataRepository repository;

    @Test
    void loadData_shouldLoadRecordsFromFile() throws Exception {
        repository.loadData();

        assertThat(repository.findAll()).isNotEmpty();
    }

    @Test
    void loadData_shouldSkipRecordsWithBlankJobTitle() throws Exception {
        repository.loadData();

        boolean hasBlankTitle = repository.findAll().stream()
                .anyMatch(j -> j.getJobTitle() == null || j.getJobTitle().isBlank());

        assertThat(hasBlankTitle).isFalse();
    }

    @Test
    void loadData_shouldMapFieldsCorrectly() throws Exception {
        repository.loadData();

        JobData first = repository.findAll().get(0);

        assertThat(first.getJobTitle()).isNotBlank();
        assertThat(first.getTimestamp()).isNotNull();
    }

    @Test
    void loadData_shouldParseSalaryToNullWhenInvalidFormat() throws Exception {
        repository.loadData();

        // Check record that salary cannot parse will be null, not crash
        long nullSalaryCount = repository.findAll().stream()
                .filter(j -> j.getSalary() == null)
                .count();

        assertThat(nullSalaryCount).isGreaterThan(0);
    }

    @Test
    void loadData_shouldParseSalaryCorrectlyForValidValues() throws Exception {
        repository.loadData();

        // Check records that salary can parse,  then value > 0
        boolean hasValidSalary = repository.findAll().stream()
                .filter(j -> j.getSalary() != null)
                .allMatch(j -> j.getSalary().compareTo(BigDecimal.ZERO) >= 0);

        assertThat(hasValidSalary).isTrue();
    }

    @Test
    void findAll_shouldReturnSameListEveryTime() throws Exception {
        repository.loadData();

        List<JobData> first = repository.findAll();
        List<JobData> second = repository.findAll();

        assertThat(first).isSameAs(second);
    }
}