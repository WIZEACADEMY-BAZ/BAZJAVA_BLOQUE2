package com.wizeline.gradle.learningjavagradle.batch;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BatchTests {
	
	@Mock
	UserProcessor userProcessor;
	
	private static final String DATOS = "mateo";
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	public void userProcessorConDatos() throws Exception {
		String procesado = userProcessor.process(DATOS);
		
		assertNotNull(procesado);
	}
	
	
	public void userProcessorSinDatos() throws Exception {
		//String procesado = 
		
		Exception exception = assertThrows(Exception.class, () -> {
			userProcessor.process(null);
		});
	}

}
