package ru.clevertec.logging.elk.starter.service;


import lombok.extern.slf4j.Slf4j;
import ru.clevertec.logging.elk.starter.config.constants.Constants;
import ru.clevertec.logging.elk.starter.config.constants.ElasticFieldName;
import uk.org.lidalia.slf4jext.Level;
import uk.org.lidalia.slf4jext.Logger;
import uk.org.lidalia.slf4jext.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

@Slf4j
public class ElkLog {
    private final Map<String, Object> loggingFieldsMap;

    private final Logger logger;
    private Level level;

    public ElkLog(String loggerName) {
        loggingFieldsMap = new HashMap<>();
        logger = LoggerFactory.getLogger(loggerName);
        level = Level.INFO;
    }

    public static ElkLog builder(String loggerName) {
        return new ElkLog(loggerName);
    }

    public ElkLog setLevel(Level level) {
        this.level = level;
        return this;
    }

    public ElkLog addField(String fieldName, Object fieldValue) {
        loggingFieldsMap.put(fieldName, fieldValue);
        return this;
    }

    public ElkLog addLoggerName(String loggerName) {
        addField(ElasticFieldName.LOGGER_NAME, loggerName);
        return this;
    }

    public ElkLog addMessage(String message) {
        return addField(ElasticFieldName.MESSAGE, message);
    }

    public ElkLog addRequest(Object request) {
        return addField(ElasticFieldName.REQUEST, request);
    }

    public ElkLog addResponse(Object response) {
        return addField(ElasticFieldName.RESPONSE, response);
    }

    public void print() {
        Object[] loggingFields = getLoggingFields();

        String[] parenthesesArray = new String[loggingFields.length];
        Arrays.fill(parenthesesArray, Constants.PARENTHESES);
        String parentheses = String.join(Constants.PARENTHESES_DELIMITER, parenthesesArray);

        logger.log(level, parentheses, loggingFields);
    }


    public void printToElk() {
        logger.log(level, Constants.EMPTY_STRING, getLoggingFields());
    }

        public void printToConsole() {
        logger.log(level, Constants.PARENTHESES, Arrays.asList(getLoggingFields()));
    }

    private Object[] getLoggingFields() {
        return loggingFieldsMap.entrySet()
                .stream()
                .map(entry -> keyValue(entry.getKey(), entry.getValue()))
                .toArray();
    }



    // todo loggerName
    // todo custom fields from yml
    // todo readme
    // todo Mishe
}
