package ru.clevertec.logging.elk.starter.config.configuration;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.status.OnConsoleStatusListener;
import ch.qos.logback.core.status.StatusManager;
import ch.qos.logback.core.util.StatusPrinter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.FileNotFoundException;

@Configuration
@RequiredArgsConstructor
public class LoggerAutoConfiguration {

    private final Environment environment;

//    @Bean
//    public Logger logger() {
//        // assume SLF4J is bound to logback in the current environment
////        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
//
//        //            File file = new File("src/main/resources/logback-spring.xml");
//
////        JoranConfigurator configurator = new JoranConfigurator();
////        configurator.setContext(context);
//        // Call context.reset() to clear any previous configuration, e.g. default
//        // configuration. For multi-step configuration, omit calling context.reset().
////            context.reset();
////        System.out.println("===========");
////            configurator.doConfigure(new File("src/main/resources/logback-spring.xml"));
////        StatusPrinter.printInCaseOfErrorsOrWarnings(context);
//
//        Logger logger = LoggerFactory.getLogger("ELKaLogger");
//        return logger;
//    }

//    @Bean
//    public LoggingSystemProperties loggingSystemProperties() {
//        LoggingSystemProperties loggingSystemProperties = new LoggingSystemProperties(environment);
//        loggingSystemProperties.se
//
//    }

//    @Bean
//    public LogbackLoggingSystem logger() {
//
//        ClassLoader classLoader = Logger.class.getClassLoader();
//        LogbackLoggingSystem logbackLoggingSystem = new LogbackLoggingSystem(classLoader);
////        LoggerConfiguration slf4j = logbackLoggingSystem.getLoggerConfiguration("slf4j");
////        System.out.println("=============" + slf4j);
//        return logbackLoggingSystem;
//    }
}

