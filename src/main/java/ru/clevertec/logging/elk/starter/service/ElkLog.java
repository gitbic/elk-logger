package ru.clevertec.logging.elk.starter.service;


import lombok.extern.slf4j.Slf4j;
import ru.clevertec.logging.elk.starter.config.constants.Constants;
import ru.clevertec.logging.elk.starter.config.constants.ElasticFieldName;
import uk.org.lidalia.slf4jext.Level;
import uk.org.lidalia.slf4jext.Logger;
import uk.org.lidalia.slf4jext.LoggerFactory;

import java.util.*;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

@Slf4j
public class ElkLog {

    private final Map<String, Object> loggingFieldsMap;
    private final Logger logger;
    private Level level;

    public ElkLog(String loggingClassName) {
        loggingFieldsMap = new LinkedHashMap<>();
        logger = LoggerFactory.getLogger(loggingClassName);
        level = Level.INFO;
    }

    public ElkLog(Class<?> clazz) {
        this(clazz.getName());
    }

    public static ElkLog builder(String loggerName) {
        return new ElkLog(loggerName);
    }

    public static ElkLog builder(Class <?> clazz) {
        return builder(clazz.getName());
    }

    public ElkLog setLevel(Level level) {
        this.level = level;
        return this;
    }

    public ElkLog addField(String fieldName, Object fieldValue) {
        loggingFieldsMap.put(fieldName, fieldValue);
        return this;
    }

    public ElkLog setLoggerName(String loggerName) {
        addField(ElasticFieldName.LOGGER_NAME, loggerName);
        return this;
    }

    public ElkLog addMessage(String message) {
        return addField(ElasticFieldName.MESSAGE, message);
    }

    public ElkLog setMessage(String message) {
        return addField(ElasticFieldName.MESSAGE, message);
    }

    public ElkLog setField(String fieldName, Object fieldValue) {
        loggingFieldsMap.put(fieldName, fieldValue);
        return this;
    }

    public ElkLog addRequest(Object request) {
        return addField(ElasticFieldName.REQUEST, request);
    }

    public ElkLog addResponse(Object response) {
        return addField(ElasticFieldName.RESPONSE, response);
    }

    public ElkLog removeMessage() {
        loggingFieldsMap.remove(ElasticFieldName.MESSAGE);
        return this;
    }

    public ElkLog removeRequest() {
        loggingFieldsMap.remove(ElasticFieldName.REQUEST);
        return this;
    }

    public ElkLog removeResponse() {
        loggingFieldsMap.remove(ElasticFieldName.RESPONSE);
        return this;
    }

    public ElkLog removeField(String fieldName) {
        loggingFieldsMap.remove(fieldName);
        return this;
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


    // todo readme
    // todo Mishe
    // todo отключать опционально логгеры
}
