package com.satheesh.portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description Main application entry point for portfolio-service microservice.
 * Scans com.satheesh.portfolio and common library package com.satheesh.common.
 */
@SpringBootApplication(scanBasePackages = {"com.satheesh.portfolio", "com.satheesh.common"})
@EnableCaching
public class PortfolioServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortfolioServiceApplication.class, args);
    }
}
