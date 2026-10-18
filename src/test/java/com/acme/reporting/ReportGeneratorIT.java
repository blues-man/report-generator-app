package com.acme.reporting;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the report generator.
 *
 * These run under failsafe rather than surefire because they start the whole
 * Spring context: JPA against H2, the security filter chain and the Netflix
 * Cloud starters all have to resolve and wire together. A dependency
 * remediation that broke any of that fails here, which is the point -- the
 * unit tests never load the context.
 */
@SpringBootTest
@AutoConfigureMockMvc
class ReportGeneratorIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("the application context loads with the remediated dependencies")
    void contextLoads() {
        // Reaching this point means every bean was constructed: the datasource,
        // Hibernate with the H2 dialect, the cache config and the security
        // filter chain. Failure here is the signal that a bump broke wiring.
        org.junit.jupiter.api.Assertions.assertNotNull(mockMvc);
    }

    @Test
    @DisplayName("the landing page is public")
    void rootIsPermitted() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("the user API rejects anonymous callers")
    void apiRequiresAuthentication() throws Exception {
        mockMvc.perform(get("/api/users"))
               .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("the user API serves JSON to an authenticated caller")
    void apiServesJsonWhenAuthenticated() throws Exception {
        mockMvc.perform(get("/api/users").with(httpBasic("user", "user123")))
               .andExpect(status().isOk())
               .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("actuator is restricted to ADMIN")
    void actuatorRequiresAdminRole() throws Exception {
        mockMvc.perform(get("/actuator/health").with(httpBasic("user", "user123")))
               .andExpect(status().isForbidden());
        mockMvc.perform(get("/actuator/health").with(httpBasic("admin", "admin123")))
               .andExpect(status().isOk());
    }
}
