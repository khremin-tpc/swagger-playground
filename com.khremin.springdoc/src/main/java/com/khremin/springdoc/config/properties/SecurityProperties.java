package com.khremin.springdoc.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(value = "security")
@Data
public class SecurityProperties {
    /**
     * Key store path. Must start with file: or classpath:
     */
    private String keyStorePath;
    /**
     * Key store password
     */
    private String keyStorePassword;
}
