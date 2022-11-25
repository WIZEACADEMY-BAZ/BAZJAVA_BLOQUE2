package com.wizeline.maven.learningjavamaven.model;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookBeanTest {

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
        bookBean.setName("AutorName");
    }

    @Test
    void getAuthor() {
        bookBean.getAutor();
    }

    @Test
    void setAuthor() {
        bookBean.setAutor("Autor1");
    }

    @Test
    void getDate() {
        bookBean.getDate();
    }

    @Test
    void setDate() {

    }
}
