package com.wizeline.gradle.practicajava.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
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

import com.wizeline.gradle.practicajava.model.EstudianteDTO;

@Configuration
public class CursoJob {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	public Job cursoBackupJob() {
		return jobBuilderFactory.get("cursoBackupJob").start(cursoBackupStep(stepBuilderFactory))
				.listener(jobExecutionListener2()).build();

	}

	@Bean
	public Step cursoBackupStep(StepBuilderFactory stepBuilderFactory) {
		return stepBuilderFactory.get("cursoBackupStep").<EstudianteDTO, String>chunk(5)
				.reader(cursoReader()).processor(cursoItemProcessor()).writer(cursoWriter())
				.build();
	}

	@Bean
	public FlatFileItemReader<EstudianteDTO> cursoReader() {
		return new FlatFileItemReaderBuilder<EstudianteDTO>().name("cursoReader")
				.resource(new ClassPathResource("csv/estudiantes.csv")).delimited()
				.names(new String[] { "matricula", "nombre", "turno", "universidad", "correo" })
				.targetType(EstudianteDTO.class).build();
	}

	@Bean
	public FlatFileItemWriter<String> cursoWriter() {
		return new FlatFileItemWriterBuilder<String>().name("cursoWriter")
				.resource(new FileSystemResource("target/test-outputs/estudiantesBackup.txt"))
				.lineAggregator(new PassThroughLineAggregator<>()).build();
	}

	@Bean
	public CursoItemProcessor cursoItemProcessor() {
		return new CursoItemProcessor();
	}

	@Bean
	public JobExecutionListener jobExecutionListener2() {
		return new BatchJobCompletionListener();
	}

}
