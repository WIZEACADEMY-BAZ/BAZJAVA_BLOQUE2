package com.wizeline.baz.LearningSpring.config;

import com.wizeline.baz.LearningSpring.batch.BatchItemProcessor;
import com.wizeline.baz.LearningSpring.batch.ConsoleItemWriter;
import com.wizeline.baz.LearningSpring.model.NotificacionesDTO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@EnableBatchProcessing
public class JobBatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job readCSVFilesJob() {
        return jobBuilderFactory
                .get("readCSVFilesJob")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").<NotificacionesDTO, NotificacionesDTO>chunk(5)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }


    @Bean
    public FlatFileItemReader<NotificacionesDTO> reader()
    {
        FlatFileItemReader<NotificacionesDTO> reader = new FlatFileItemReader<NotificacionesDTO>();

        reader.setResource(new FileSystemResource("src/wizelineCsv.csv"));

        reader.setLinesToSkip(1);

        reader.setLineMapper(new DefaultLineMapper() {
            {
                setLineTokenizer(new DelimitedLineTokenizer() {
                    {
                        setNames(new String[] { "id", "push_Id", "phone", "email" });
                    }
                });
                setFieldSetMapper(new BeanWrapperFieldSetMapper<NotificacionesDTO>() {
                    {
                        setTargetType(NotificacionesDTO.class);
                    }
                });
            }
        });
        return reader;
    }

    @Bean
    public ItemProcessor<NotificacionesDTO, NotificacionesDTO> processor() {
        return new BatchItemProcessor();
    }

    @Bean
    public ConsoleItemWriter<NotificacionesDTO> writer()
    {
        return new ConsoleItemWriter<NotificacionesDTO>();
    }

}
