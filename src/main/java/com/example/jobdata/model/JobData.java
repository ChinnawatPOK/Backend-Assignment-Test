package com.example.jobdata.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobData {

    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("employer")
    private String employer;

    @JsonProperty("location")
    private String location;

    @JsonProperty("job_title")
    private String jobTitle;

    @JsonProperty("years_at_employer")
    private String yearsAtEmployer;

    @JsonProperty("years_of_experience")
    private String yearsOfExperience;

    @JsonProperty("salary")
    private BigDecimal salary;

    @JsonProperty("signing_bonus")
    private String signingBonus;

    @JsonProperty("annual_bonus")
    private String annualBonus;

    @JsonProperty("annual_stock_value")
    private String annualStockValue;

    @JsonProperty("gender")
    private String gender;

    @JsonProperty("additional_comments")
    private String additionalComments;
}
