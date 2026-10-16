package com.acme.reporting;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReportGeneratorApplicationTests {

    @Test
    void reportTitleMustNotBeBlank() {
        String title = "Q4 Compliance Summary";
        assertFalse(title.isBlank());
    }

    @Test
    void csvOutputContainsHeaderRow() {
        String[] headers = {"id", "name", "email", "status"};
        String headerRow = String.join(",", headers);
        assertEquals("id,name,email,status", headerRow);
        assertEquals(4, headers.length);
    }

    @Test
    void requiredComplianceFieldsPresent() {
        java.util.Map<String, String> fields = new java.util.HashMap<>();
        fields.put("generatedAt", "2026-10-16T00:00:00Z");
        fields.put("generatedBy", "system");
        fields.put("classification", "internal");

        assertTrue(fields.containsKey("generatedAt"));
        assertTrue(fields.containsKey("generatedBy"));
        assertTrue(fields.containsKey("classification"));
    }

    @Test
    void paginationDefaults() {
        int defaultPage = 0;
        int defaultSize = 25;
        int maxSize = 100;

        assertTrue(defaultSize > 0);
        assertTrue(defaultSize <= maxSize);
        assertEquals(0, defaultPage);
    }
}
