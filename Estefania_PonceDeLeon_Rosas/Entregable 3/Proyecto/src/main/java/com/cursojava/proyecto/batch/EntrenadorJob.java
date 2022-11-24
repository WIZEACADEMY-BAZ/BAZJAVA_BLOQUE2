package com.cursojava.proyecto.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Create UserJob.
 */
@Configuration
public class EntrenadorJob {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job printEntrenadorJob() {
        return jobBuilderFactory.get("printEntrenadorJob")
                .incrementer(new RunIdIncrementer())
                .flow(printEntrenadorStep())
                .end().listener(new BatchJobCompletionListener())
                .build();

    }

    @Bean
    public Step printEntrenadorStep() {
        return stepBuilderFactory.get("printEntrenadorStep")
                .<String, String>chunk(3)
                .reader(new EntrenadorReader())
                .processor(new EntrenadorProcessor())
                .writer(new EntrenadorWriter())
                .build();
    }



}
