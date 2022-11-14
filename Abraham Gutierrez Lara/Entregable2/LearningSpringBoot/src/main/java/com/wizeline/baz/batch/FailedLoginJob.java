package com.wizeline.baz.batch;

import java.util.Properties;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.kafka.KafkaItemReader;
import org.springframework.batch.item.kafka.KafkaItemWriter;
import org.springframework.batch.item.kafka.builder.KafkaItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import com.wizeline.baz.model.batch.BlockedUserAccount;
import com.wizeline.baz.model.batch.FailedLoginInfo;

@Configuration
public class FailedLoginJob {
	
	@Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    private KafkaTemplate<String, BlockedUserAccount> kafkaTemplate;
    
    @Autowired
    private FailedLoginItemProcessor itemProcessor;
	
	@Bean
	public Job failedLoginsReport() throws Exception {
		return jobBuilderFactory.get("failedLoginsReport")
				.start(failedLoginsReportStep())
				.listener(jobExecutionListener())
				.build();
	}
	
	@Bean
	public KafkaItemReader<String, FailedLoginInfo> kafkaFailedLoginReader() {
		Properties props = new Properties();
		return new KafkaItemReaderBuilder<String, FailedLoginInfo>()
				.partitions(0)
				.consumerProperties(props)
				.name("kafkaFailedLoginReader")
				.saveState(true)
				.topic("failed-login-users")
				.build();
	}
	
	@Bean
	public KafkaItemWriter<String, BlockedUserAccount> kafkaBlockedUserAccountWriter() throws Exception {
		KafkaItemWriter<String, BlockedUserAccount> writer = new KafkaItemWriter<>();
		writer.setKafkaTemplate(kafkaTemplate);
		writer.setItemKeyMapper(BlockedUserAccount::getId);
		writer.setDelete(false);
		writer.afterPropertiesSet();
		return writer;
	}
	
	@Bean
	public Step failedLoginsReportStep() throws Exception {
		return stepBuilderFactory.get("failedLoginsReportStep")
				.<FailedLoginInfo, BlockedUserAccount>chunk(100)
				.reader(kafkaFailedLoginReader())
				.writer(kafkaBlockedUserAccountWriter())
				.processor(itemProcessor)
				.build();
	}
	
	@Bean
	 public JobExecutionListener jobExecutionListener() {
	     return new BatchJobCompletionListener();
	 }
	
}
