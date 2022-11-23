package com.wizeline.baz.batch;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
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
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.wizeline.baz.model.KafkaMessage;
import com.wizeline.baz.utils.Constants;

@Configuration
public class FailedLoginJob {
	
	@Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    
    @Autowired
    private ProducerFactory<String, KafkaMessage> producerFactory;
    
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
	public KafkaItemReader<String, KafkaMessage> kafkaFailedLoginReader() {
		Properties props = new Properties();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, Constants.FAILED_LOGINS_CONSUMER_GROUP);
		return new KafkaItemReaderBuilder<String, KafkaMessage>()
				.partitions(0)
				.consumerProperties(props)
				.name("kafkaFailedLoginReader")
				.saveState(true)
				.topic(Constants.FAILED_LOGINS_TOPIC)
				.build();
	}
	
	@Bean
	public KafkaItemWriter<String, KafkaMessage> kafkaBlockedUserAccountWriter() throws Exception {
		KafkaItemWriter<String, KafkaMessage> writer = new KafkaItemWriter<>();
		KafkaTemplate<String, KafkaMessage> kafkaTemplate = new KafkaTemplate<>(producerFactory);
		kafkaTemplate.setDefaultTopic(Constants.BLOCK_USERS_TOPIC);
		writer.setKafkaTemplate(kafkaTemplate);
		writer.setItemKeyMapper(KafkaMessage::getId);
		writer.setDelete(false);
		writer.afterPropertiesSet();
		return writer;
	}
	
	@Bean
	public Step failedLoginsReportStep() throws Exception {
		return stepBuilderFactory.get("failedLoginsReportStep")
				.<KafkaMessage, KafkaMessage>chunk(100)
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
