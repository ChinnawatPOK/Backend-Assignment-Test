package com.example.jobdata.util;

import com.example.jobdata.model.JobData;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class FieldExtractor {

    public Map<String, Object> extract(JobData job, List<String> fields) {
        Map<String, Object> result = new LinkedHashMap<>();

        for (String field : fields) {
            switch (field.toLowerCase().trim()) {
                case "timestamp" -> result.put("timestamp", job.getTimestamp());
                case "employer" -> result.put("employer", job.getEmployer());
                case "location" -> result.put("location", job.getLocation());
                case "job_title" -> result.put("job_title", job.getJobTitle());
                case "years_at_employer" -> result.put("years_at_employer", job.getYearsAtEmployer());
                case "years_of_experience" -> result.put("years_of_experience", job.getYearsOfExperience());
                case "salary" -> result.put("salary", job.getSalary());
                case "signing_bonus" -> result.put("signing_bonus", job.getSigningBonus());
                case "annual_bonus" -> result.put("annual_bonus", job.getAnnualBonus());
                case "annual_stock_value" -> result.put("annual_stock_value", job.getAnnualStockValue());
                case "gender" -> result.put("gender", job.getGender());
                case "additional_comments" -> result.put("additional_comments", job.getAdditionalComments());
                default -> {}
            }
        }

        return result;
    }
}
