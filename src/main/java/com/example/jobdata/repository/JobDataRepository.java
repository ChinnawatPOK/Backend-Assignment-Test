package com.example.jobdata.repository;

import com.example.jobdata.model.JobData;
import com.example.jobdata.util.SalaryParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@RequiredArgsConstructor
public class JobDataRepository {

    private final ObjectMapper objectMapper;
    private final SalaryParser salaryParser;
    private List<JobData> jobDataList = new ArrayList<>();


    @PostConstruct
    public void loadData() throws IOException {
        ClassPathResource resource = new ClassPathResource("data/salary_survey.json");
        List<Map<String, String>> rawList = objectMapper.readValue(
                resource.getInputStream(),
                new TypeReference<>() {}
        );

        for (Map<String, String> raw : rawList) {
            JobData job = JobData.builder()
                    .timestamp(raw.get("Timestamp"))
                    .employer(raw.get("Employer"))
                    .location(raw.get("Location"))
                    .jobTitle(raw.get("Job Title"))
                    .yearsAtEmployer(raw.get("Years at Employer"))
                    .yearsOfExperience(raw.get("Years of Experience"))
                    .salary(salaryParser.parseSalary(raw.get("Salary")))
                    .signingBonus(raw.get("Signing Bonus"))
                    .annualBonus(raw.get("Annual Bonus"))
                    .annualStockValue(raw.get("Annual Stock Value/Bonus"))
                    .gender(raw.get("Gender"))
                    .additionalComments(raw.get("Additional Comments"))
                    .build();

            if (job.getJobTitle() != null && !job.getJobTitle().isBlank()) {
                jobDataList.add(job);
            }
        }
    }

    public List<JobData> findAll() {
        return jobDataList;
    }

}
