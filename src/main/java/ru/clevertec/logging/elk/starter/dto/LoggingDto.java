package ru.clevertec.logging.elk.starter.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder(toBuilder = true)
public class LoggingDto {
    private String applicationId;
    private String templateName;
    private String destinationEmail;
    private String subject;
    private Map<String, String> templateFields;
}
