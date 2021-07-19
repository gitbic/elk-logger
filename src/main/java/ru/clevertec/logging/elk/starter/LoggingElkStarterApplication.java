package ru.clevertec.logging.elk.starter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.List;


@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class LoggingElkStarterApplication {
	public static void main(String[] args) {
		SpringApplication.run(LoggingElkStarterApplication.class, args);
	}

//	@EventListener(ApplicationReadyEvent.class)
//	public void doSomething() {
//		log.warn("=========Hello from LoggingElkStarter to logstash==========");
//	}

}
