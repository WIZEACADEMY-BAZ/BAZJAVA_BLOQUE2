package com.wizeline.maven.learningjavamaven.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookBeanTest {
    @InjectMocks
    private BookBean bookBean;

    @Test
    void getId() {
        bookBean.getId();
    }

    @Test
    void setId() {


    }

    @Test
    void getName() {
        bookBean.getName();
    }

    @Test
    void setName() {
        bookBean.setName("dfg");
    }

    @Test
    void getAuthor() {
        bookBean.getAuthor();
    }

    @Test
    void setAuthor() {
        bookBean.setAuthor("anacleto");
    }

    @Test
    void getDate() {
        bookBean.getDate();
    }

    @Test
    void setDate() {

    }
}