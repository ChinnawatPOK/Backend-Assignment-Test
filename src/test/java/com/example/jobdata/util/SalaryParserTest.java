package com.example.jobdata.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SalaryParserTest {

    private final SalaryParser parser = new SalaryParser();

    // Normal cases

    @Test
    void parse_normalNumber_shouldReturnCorrectValue() {
        assertThat(parser.parseSalary("122000")).isEqualByComparingTo("122000");
    }

    @Test
    void parse_withComma_shouldReturnCorrectValue() {
        assertThat(parser.parseSalary("83,000")).isEqualByComparingTo("83000");
    }

    // K suffix

    @Test
    void parse_withLowercaseK_shouldMultiplyBy1000() {
        assertThat(parser.parseSalary("135k")).isEqualByComparingTo("135000");
    }

    @Test
    void parse_withUppercaseK_shouldMultiplyBy1000() {
        assertThat(parser.parseSalary("275K")).isEqualByComparingTo("275000");
    }

    @Test
    void parse_withDecimalK_shouldMultiplyBy1000() {
        assertThat(parser.parseSalary("61.7k")).isEqualByComparingTo("61700");
    }

    // Currency symbols

    @Test
    void parse_withPoundSymbol_shouldReturnCorrectValue() {
        assertThat(parser.parseSalary("£61000")).isEqualByComparingTo("61000");
    }

    @Test
    void parse_withEuroAndK_shouldReturnCorrectValue() {
        assertThat(parser.parseSalary("61.7k €")).isEqualByComparingTo("61700");
    }

    @Test
    void parse_withDollarSign_shouldReturnCorrectValue() {
        assertThat(parser.parseSalary("$50.00")).isEqualByComparingTo("50.00");
    }

    // Null / blank

    @Test
    void parse_nullValue_shouldReturnNull() {
        assertThat(parser.parseSalary(null)).isNull();
    }

    @Test
    void parse_blankValue_shouldReturnNull() {
        assertThat(parser.parseSalary("")).isNull();
        assertThat(parser.parseSalary("   ")).isNull();
    }

    // Edge cases and others case

    @Test
    void parse_garbageText_shouldReturnNull() {
        assertThat(parser.parseSalary("a trap a day makes the boner go away")).isNull();
    }

    @Test
    void parse_onlyCurrencySymbol_shouldReturnNull() {
        assertThat(parser.parseSalary("$")).isNull();
        assertThat(parser.parseSalary("€")).isNull();
    }
}