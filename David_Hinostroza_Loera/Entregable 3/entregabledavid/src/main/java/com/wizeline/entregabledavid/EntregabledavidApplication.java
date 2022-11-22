package com.wizeline.entregabledavid;

import java.io.IOException;
import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })

@EnableKafka
public class EntregabledavidApplication {

	private static final Logger LOGGER = Logger.getLogger(EntregabledavidApplication.class.getName());

	public static void main(String[] args) throws IOException {
		SpringApplication.run(EntregabledavidApplication.class, args);
		LOGGER.info("LearningJava - Iniciado servicio REST ...");

	}

}