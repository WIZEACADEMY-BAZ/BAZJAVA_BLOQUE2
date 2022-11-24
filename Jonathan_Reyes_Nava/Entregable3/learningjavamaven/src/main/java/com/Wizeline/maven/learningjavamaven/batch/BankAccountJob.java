package com.Wizeline.maven.learningjavamaven.batch;

import com.Wizeline.maven.learningjavamaven.model.BankAccountDTO;
import org.springframework.batch.core.JobExecutionListener;
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

@Configuration
public class BankAccountJob {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Bean
    public FlatFileItemReader<BankAccountDTO> bankAccountsReader() {
            return new FlatFileItemReaderBuilder<BankAccountDTO>()
                    .name("bankAccountsReader")
                    .resource(new ClassPathResource("csv/.csv"))
                    .delimited().names(new String[] { "country", "accountName", "accountType", "accountBalance", "userName"})
                    .targetType(BankAccountDTO.class).build();
    }

    @Bean
    public FlatFileItemWriter<String> bankAccountsWriter() {
            return new FlatFileItemWriterBuilder<String>()
                    .name("bankAccountsWriter")
                    .resource(new FileSystemResource(
                            "target/test-outputs/bankAccountsBackup.txt"))
                    .lineAggregator(new PassThroughLineAggregator<>()).build();
    }
    @Bean
    public JobExecutionListener jobExecutionListener() {
            return new BatchJobCompletionListener();
    }
}
