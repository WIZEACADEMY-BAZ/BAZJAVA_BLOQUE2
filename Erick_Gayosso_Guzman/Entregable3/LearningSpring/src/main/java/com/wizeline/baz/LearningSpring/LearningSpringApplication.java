package com.wizeline.baz.LearningSpring;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.batch.core.Job;


@SpringBootApplication
@EnableScheduling
@EnableKafka
public class LearningSpringApplication {


    public static void main(String[] args) {
        SpringApplication.run(LearningSpringApplication.class, args);
    }

}
