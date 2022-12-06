package com.wizeline.gradle.learningjavagradle.batch;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class UserProcessorTest {

	@Mock
	UserProcessor userProcessor;
	
	private static final String DATOS = "alex";
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	
	public void Testprocess() throws Exception {
		String procesado = userProcessor.process(DATOS);
		
		assertNotNull(procesado);
	}
	
	
	public void TestprocessSDatos() throws Exception { 
		
		Exception exception = assertThrows(Exception.class, () -> {
			userProcessor.process(null);
		});
	}
	

}
