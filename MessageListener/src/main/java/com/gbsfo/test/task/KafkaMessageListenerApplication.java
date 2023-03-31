package com.gbsfo.test.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

@SpringBootApplication
public class KafkaMessageListenerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaMessageListenerApplication.class, args);
	}
}
