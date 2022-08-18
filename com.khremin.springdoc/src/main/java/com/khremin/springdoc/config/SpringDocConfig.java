package com.khremin.springdoc.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .version("1.0.0-RELEASE")
                        .title("Test title")
                        .description("Not so long description")
                        .summary("The summary is so summery.")
                        .contact(new Contact().name("John Doe").email("fake@khremin.com").url("https://khremin.com")))
                .components(new Components()
                        .addSecuritySchemes("Bearer token",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("Bearer").bearerFormat("JWT")));
    }
}