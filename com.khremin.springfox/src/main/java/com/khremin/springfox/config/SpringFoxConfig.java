package com.khremin.springfox.config;

import io.swagger.models.auth.In;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

@Configuration
public class SpringFoxConfig {

    private static final ApiInfo API_INFO = new ApiInfo(
            "API for playground",
            "Not so long description for API.",
            "1.0.0-RELEASE",
            "https://khremin.com",
            new Contact("John Doe", "https://khremin.com", "fake@khremin.com"),
            "",
            "",
            Collections.emptyList()
    );

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(API_INFO)
                .useDefaultResponseMessages(false)
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts())
                .directModelSubstitute(LocalTime.class, String.class)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/**")
                        .and(PathSelectors.ant("/error/**").negate())) // if you would like to exclude something
                .build();
    }


    // description for which schemas will be supported from the Swagger UI
    private List<SecurityScheme> securitySchemes() {
        return Collections.singletonList(
                new ApiKey(HttpHeaders.AUTHORIZATION, HttpHeaders.AUTHORIZATION, In.HEADER.name())
        );
    }

    private List<SecurityContext> securityContexts() {
        return Collections.singletonList(
                SecurityContext.builder()
                        .securityReferences(
                                Collections.singletonList(
                                        new SecurityReference(HttpHeaders.AUTHORIZATION, new AuthorizationScope[0])
                                )
                        )
                        .operationSelector(
                                operationContext ->
                                        !operationContext.requestMappingPattern().startsWith("/auth") // this method doesn't require authorization
                        )
                        .build()
        );
    }
}