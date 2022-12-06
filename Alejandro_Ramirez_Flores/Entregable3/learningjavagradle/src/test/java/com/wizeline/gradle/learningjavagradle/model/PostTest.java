package com.wizeline.gradle.learningjavagradle.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class PostTest {
	
	    @InjectMocks
	    private Post post;

	@Test
	void testPost() {
	}

	@Test
	void testGetUserId() {
		post.getUserId();
	}

	@Test
	void testSetUserId() {
		post.setUserId("3345");
	}

	@Test
	void testGetId() {
		post.getId();
	}

	@Test
	void testSetId() {
		post.setId((long) -23);
	}

	@Test
	void testGetTitle() {
		post.getTitle();
	}

	@Test
	void testSetTitle() {
		post.setTitle("NingunTitulo");
	}

	@Test
	void testGetBody() {
		post.getBody();
	}

	@Test
	void testSetBody() {
		post.getBody();
	}

}
