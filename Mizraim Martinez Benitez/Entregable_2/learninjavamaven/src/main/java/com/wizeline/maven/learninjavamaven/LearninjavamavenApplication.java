package com.wizeline.maven.learninjavamaven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@EnableFeignClients
@EnableKafka
public class LearninjavamavenApplication {


	public static void main(String[] args){
		SpringApplication.run(LearninjavamavenApplication.class, args);
	}


}
