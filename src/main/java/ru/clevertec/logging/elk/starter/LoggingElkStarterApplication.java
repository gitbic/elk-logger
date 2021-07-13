package ru.clevertec.logging.elk.starter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;



@SpringBootApplication
@RequiredArgsConstructor
public class LoggingElkStarterApplication {

//	private final Logger logger;

	public static void main(String[] args) {
		SpringApplication.run(LoggingElkStarterApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void doSomething() {
		System.out.println("Hello");
//		log.warn("Hello from logger");
//		logger.warn("Warn logger");
//		System.out.println("logger name: " + logger.getName());
	}

}
