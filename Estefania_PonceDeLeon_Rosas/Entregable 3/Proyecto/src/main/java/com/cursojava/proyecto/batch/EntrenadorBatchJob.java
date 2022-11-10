package com.cursojava.proyecto.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.PassThroughLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import com.cursojava.proyecto.model.EntrenadorDTO;

@Configuration
@EnableBatchProcessing
public class EntrenadorBatchJob {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job entrenadorBackupJob() {
        return jobBuilderFactory.get("entrenadorBackupJob")
                .start(entrenadorBackupStep(stepBuilderFactory))
                .listener(jobExecutionListener())
                .build();
    }

    @Bean
    public Step entrenadorBackupStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("entrenadorBackupStep")
                .<EntrenadorDTO, String>chunk(5).reader(entrenadorReader())
                .processor(bankAccountItemProcessor()).writer(entrenadorWriter()).build();
    }

    @Bean
    public FlatFileItemReader<EntrenadorDTO> entrenadorReader() {
        return new FlatFileItemReaderBuilder<EntrenadorDTO>()
                .name("entrenadorReader")
                .resource(new ClassPathResource("csv/entrenador.csv"))
                .delimited().names(new String[] {"nombre", "rango"})
                .targetType(EntrenadorDTO.class).build();
    }

    @Bean
    public FlatFileItemWriter<String> entrenadorWriter() {
        return new FlatFileItemWriterBuilder<String>()
                .name("entrenadorWriter")
                .resource(new FileSystemResource("build/test-outputs/entrenadorBackup.txt"))
                .lineAggregator(new PassThroughLineAggregator<>()).build();
    }

    @Bean
    public EntrenadorItemProcessor bankAccountItemProcessor() {
        return new EntrenadorItemProcessor();
    }

    @Bean
    public JobExecutionListener jobExecutionListener() {
        return new BatchJobCompletionListener();
    }

}
