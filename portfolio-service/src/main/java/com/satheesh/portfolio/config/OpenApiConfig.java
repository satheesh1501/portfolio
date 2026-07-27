package com.satheesh.portfolio.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Satheesh Kumar P
 * @since 2026-07-27
 * @version 1.0.0
 * 
 * @description OpenAPI 3.0 configuration for Swagger UI documentation.
 * Accessible at: http://localhost:8080/swagger-ui.html
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Satheesh Kumar P — Portfolio Microservice API")
                        .version("1.0.0")
                        .description("Production-Grade Developer Portfolio Core Microservice REST API. " +
                                "Provides contact handling, project showcases, resume analytics, and AI chatbot integration.")
                        .contact(new Contact()
                                .name("Satheesh Kumar P")
                                .email("psatheesh1501@gmail.com")
                                .url("https://github.com/satheesh1501/portfolio"))
                        .license(new License().name("MIT License").url("https://opensource.org/licenses/MIT")));
    }
}
