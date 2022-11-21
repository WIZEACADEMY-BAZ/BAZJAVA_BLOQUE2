package com.wizeline.gradle.learningjavagradle.controller;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wizeline.gradle.learningjavagradle.batch.UserJob;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Batch", description = "Inicia manualmente un job.")
@RestController
@RequestMapping(path = "/batch")
public class BatchController {

	@Autowired
	private JobLauncher jobLauncher;

	@Autowired
	private UserJob userJob;

	@GetMapping(path = "/start")
	public ResponseEntity<String> startBatch(){
		JobParameters parameters = new JobParametersBuilder()
				.addLong("startAt", System.currentTimeMillis()).toJobParameters();

		try {
			jobLauncher.run(userJob.printUsersJob(), parameters);
		} catch (JobExecutionAlreadyRunningException | JobRestartException
				| JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("Batch Process started!!", HttpStatus.OK);
	}

}