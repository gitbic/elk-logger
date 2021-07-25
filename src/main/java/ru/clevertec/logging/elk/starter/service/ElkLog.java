package ru.clevertec.logging.elk.starter.service;


import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.argument.StructuredArgument;
import ru.clevertec.logging.elk.starter.config.constants.Constants;
import ru.clevertec.logging.elk.starter.config.constants.ElasticFieldName;
import uk.org.lidalia.slf4jext.Level;
import uk.org.lidalia.slf4jext.Logger;
import uk.org.lidalia.slf4jext.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.logstash.logback.argument.StructuredArguments.keyValue;

@Slf4j
public class ElkLog {
    private final List<StructuredArgument> loggingFields;

    private final Logger logger ;
    private Level level;

    public ElkLog(String loggerName) {
        loggingFields = new ArrayList<>();
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
        loggingFields.add(keyValue(fieldName, fieldValue));
        return this;
    }

    public ElkLog addLoggerName(String loggerName){
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
        Object[] logFields = loggingFields.toArray();

        String[] parenthesesArray = new String[logFields.length];
        Arrays.fill(parenthesesArray, Constants.PARENTHESES);
        String parentheses = String.join(Constants.PARENTHESES_DELIMITER, parenthesesArray);

        logger.log(level, parentheses, logFields);
    }


    public void printToElk() {
        logger.log(level, Constants.EMPTY_STRING, loggingFields.toArray());
    }

    public void printToConsole() {
        logger.log(level, Constants.PARENTHESES, loggingFields);
    }

    // todo custom fields from yml
    // todo readme
    // todo Mishe
}
