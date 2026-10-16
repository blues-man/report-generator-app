package com.acme.reporting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Spring Boot 2 application using deprecated Netflix OSS components.
 * 
 * Deprecated features used:
 * - @EnableHystrix (deprecated in favor of Spring Cloud Circuit Breaker)
 * - @EnableZuulProxy (deprecated in favor of Spring Cloud Gateway)
 * - @RibbonClient (deprecated in favor of Spring Cloud LoadBalancer)
 */
@SpringBootApplication
@EnableHystrix
@RibbonClient(name = "user-service")
public class ReportGeneratorApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ReportGeneratorApplication.class, args);
    }
}
