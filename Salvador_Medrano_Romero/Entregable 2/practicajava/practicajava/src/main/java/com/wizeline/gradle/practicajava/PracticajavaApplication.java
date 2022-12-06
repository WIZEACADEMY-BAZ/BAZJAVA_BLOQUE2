package com.wizeline.gradle.practicajava;

import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@ComponentScan("com.wizeline")
@EnableFeignClients
@EnableKafka
public class PracticajavaApplication{
	
	@Bean
	   public RestTemplate getRestTemplate() {
	      return new RestTemplate();
	   }
	
	public static final Logger LOGGER = Logger.getLogger(PracticajavaApplication.class.getName());


	public static void main(String[] args){
		SpringApplication.run(PracticajavaApplication.class, args);

	}
}
