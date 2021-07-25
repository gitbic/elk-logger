package ru.clevertec.logging.elk.starter.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "logging-api")
public class LoggingApiProperties {
}
