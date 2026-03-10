package com.example.jobdata.service;

import com.example.jobdata.dto.ApiResponse;
import com.example.jobdata.dto.JobDataFilter;
import com.example.jobdata.model.JobData;
import com.example.jobdata.repository.JobDataRepository;
import com.example.jobdata.util.FieldExtractor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class JobDataService {

    private final JobDataRepository repository;
    private final FieldExtractor fieldExtractor;

    public JobDataService(JobDataRepository repository, FieldExtractor fieldExtractor) {
        this.repository = repository;
        this.fieldExtractor = fieldExtractor;
    }

    public ApiResponse query(JobDataFilter filter) {
        Stream<JobData> allDataLoaded = repository.findAll().stream();

        // Filtering
        List<JobData> filtered = allDataLoaded.filter(j -> matchesFilter(j, filter))
                .collect(Collectors.toList());

        // Sorting
        applySorting(filtered, filter);

        // Sparse fields
        if (filter.getFields() != null && !filter.getFields().isEmpty()) {
            List<Map<String, Object>> sparseData = filtered.stream()
                    .map(job -> fieldExtractor.extract(job, filter.getFields()))
                    .collect(Collectors.toList());

            return ApiResponse.builder()
                    .total(filtered.size())
                    .sparseData(sparseData)
                    .build();
        }

        return ApiResponse.builder()
                .total(filtered.size())
                .data(filtered)
                .build();

    }

    private boolean matchesFilter(JobData job, JobDataFilter filter) {
        if (!matchesJobTitle(job, filter.getJobTitle())) return false;
        if (!matchesGender(job, filter.getGender())) return false;
        if (!matchesSalary(job, filter)) return false;
        return true;
    }

    private boolean matchesJobTitle(JobData job, String keyword) {
        if (keyword == null || keyword.isBlank()) return true;
        return job.getJobTitle() != null &&
                job.getJobTitle().toLowerCase().contains(keyword.toLowerCase());
    }

    private boolean matchesGender(JobData job, String gender) {
        if (gender == null || gender.isBlank()) return true;
        return job.getGender() != null &&
                job.getGender().equalsIgnoreCase(gender);
    }

    private boolean matchesSalary(JobData job, JobDataFilter filter) {
        BigDecimal salary = job.getSalary();
        if (filter.getSalaryGte() != null &&
                (salary == null || salary.compareTo(filter.getSalaryGte()) < 0)) return false;
        if (filter.getSalaryLte() != null &&
                (salary == null || salary.compareTo(filter.getSalaryLte()) > 0)) return false;
        if (filter.getSalaryEq() != null &&
                (salary == null || salary.compareTo(filter.getSalaryEq()) != 0)) return false;
        return true;
    }

    private void applySorting(List<JobData> list, JobDataFilter filter) {
        if (filter.getSort() == null || filter.getSort().isBlank()) return;

        Comparator<JobData> comparator = switch (filter.getSort().toLowerCase()) {
            case "job_title" -> Comparator.comparing(j -> nullSafe(j.getJobTitle()));
            case "salary" -> Comparator.comparing(j -> nullSafe(j.getSalary()));
            case "gender" -> Comparator.comparing(j -> nullSafe(j.getGender()));
            case "employer" -> Comparator.comparing(j -> nullSafe(j.getEmployer()));
            case "location" -> Comparator.comparing(j -> nullSafe(j.getLocation()));
            case "years_of_experience" -> Comparator.comparing(j -> nullSafe(j.getYearsOfExperience()));
            default -> null;
        };

        if (comparator == null) return;
        if ("DESC".equalsIgnoreCase(filter.getSortType())) comparator = comparator.reversed();

        list.sort(comparator);
    }

    private String nullSafe(String v) {
        return v != null ? v : "";
    }

    private BigDecimal nullSafe(BigDecimal v) {
        return v != null ? v : BigDecimal.ZERO;
    }
}
