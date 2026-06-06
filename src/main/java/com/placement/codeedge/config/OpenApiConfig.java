package com.placement.codeedge.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI codeEdgeOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("CodeEdge API")
                        .description("SDE Intern Campus Placement Preparation Platform REST API")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("CodeEdge")
                                .url("http://localhost:8080")));
    }
}
