package com.baz.wizeline.learningspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;

import java.util.logging.Logger;


@SpringBootApplication
@EnableFeignClients
@EnableKafka
public class LearningspringApplication{

    private static final Logger LOGGER = Logger.getLogger(LearningspringApplication.class.getName());

    public static void main(String[] args){
        SpringApplication.run(LearningspringApplication.class, args);
        LOGGER.info("LearningJava - Iniciado servicio - REST ...");
    }

}
