package ru.clevertec.logging.elk.starter.dto;


import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.argument.StructuredArgument;
import ru.clevertec.logging.elk.starter.constatns.Constants;
import ru.clevertec.logging.elk.starter.constatns.FieldName;
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

    private static final Logger logger = LoggerFactory.getLogger(Constants.LOGGER_NAME);
    private Level level;

    public ElkLog(String loggerName) {
        loggingFields = new ArrayList<>();
        level = Level.INFO;
        addField(FieldName.LOGGER_NAME, loggerName);
    }

    public ElkLog setLevel(Level level) {
        this.level = level;
        return this;
    }

    public static ElkLog builder(String loggerName) {
        return new ElkLog(loggerName);
    }

    public ElkLog addField(String fieldName, Object fieldValue) {
        loggingFields.add(keyValue(fieldName, fieldValue));
        return this;
    }

    public ElkLog addMessage(String message) {
        return addField(FieldName.MESSAGE, message);
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

    // todo request response
    // todo custom fields from yml
    // todo readme
    // todo Mishe
}
