package com.wizeline.gradle.learningjavagradle.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookBeanTest {
	
	@InjectMocks
    private BookBean bookBean;

	@Test
	void testSetId() {
		bookBean.setId(30L);
	}

	@Test
	void testSetName() {
		bookBean.setName("Alex");
	}

	@Test
	void testSetAuthor() {
		bookBean.setAuthor("Alejandro");
	}

	@Test
	void testGetDate() {
		bookBean.getDate();
	}

	@Test
	void testSetDate() {
		//bookBean.setDate("23/11/22");
	}

	@Test
	void testGetId() {
		bookBean.getId();
	}

	@Test
	void testGetName() {
		bookBean.getName();
	}

	@Test
	void testGetAuthor() {
		bookBean.setAuthor("alex");
	}

}
