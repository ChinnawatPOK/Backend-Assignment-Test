package com.example.jobdata.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class SalaryParser {

    public BigDecimal parseSalary(String value) {
        if (value == null || value.isBlank()) return null;
        try {
            // Get rid of currency symbols และ whitespace
            String cleaned = value
                    .toLowerCase()
                    .replaceAll("[£€$,\\s]", "")
                    .replaceAll("[^0-9.k]", "")
                    .trim();

            if (cleaned.isEmpty()) return null;

            if (cleaned.endsWith("k")) {
                String base = cleaned.replace("k", "");
                if (base.isEmpty()) return null;
                return new BigDecimal(base).multiply(new BigDecimal("1000"));
            }

            return new BigDecimal(cleaned);

        } catch (NumberFormatException e) {
            log.warn("Got some record invalid salary format: '{}'", value);
            return null;
        }
    }
}
