package ru.clevertec.logging.elk.starter.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.argument.StructuredArgument;

import java.util.ArrayList;
import java.util.List;

import static net.logstash.logback.argument.StructuredArguments.a;
import static net.logstash.logback.argument.StructuredArguments.keyValue;

@Slf4j
@Data
//@Builder(toBuilder = true)
public class ElkLog {
    //    private Map<String, Object> loggingFieldsByNames = new HashMap<>();
    private List<StructuredArgument> loggingFields;

    public ElkLog(String loggerName) {
        loggingFields = new ArrayList<>();
        addField("loggerName", loggerName);
    }

    public static ElkLog builder(String className) {
        return new ElkLog(className);
    }

    public ElkLog addField(String fieldName, Object fieldValue) {
//        loggingFieldsByNames.put(fieldName, fieldValue);
        loggingFields.add(keyValue(fieldName, fieldValue));
        return this;
    }

    public ElkLog addMessage(String message) {
        return addField("message", message);
    }

    public void print() {
        Object[] objects = loggingFields.toArray();
        log.info("", objects);
    }
// request response
    // to log to Elk
    public void printToElk() {
//        log.info("{}, {}, {}, {}, {}, {}, {}", keyValue("message", "Send processed template to email"), keyValue("senderEmail", senderEmail), keyValue("senderEmailAlias", senderEmailAlias), keyValue("destinationEmail", destinationEmail),
//                keyValue("emailForReply", emailForReply), keyValue("emailSubject", emailSubject), keyValue("templateName", templateName));
    }

    public void printToConsole() {

    }


}
