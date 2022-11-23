package com.wizeline.maven.learningjavamaven.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostTest {

    @InjectMocks
    private Post post;

    @Test
    void getUserId() {
        post.getUserId();
    }

    @Test
    void setUserId() {
        post.setUserId("2234");
    }

    @Test
    void getId() {
        post.getId();
    }

    @Test
    void setId() {
        post.setId((long) -23);
    }

    @Test
    void getTitle() {
        post.getTitle();
    }

    @Test
    void setTitle() {
        post.setTitle("Hello");
    }

    @Test
    void getBody() {
        post.getBody();
    }

    @Test
    void setBody() {
        post.getBody();
    }
}