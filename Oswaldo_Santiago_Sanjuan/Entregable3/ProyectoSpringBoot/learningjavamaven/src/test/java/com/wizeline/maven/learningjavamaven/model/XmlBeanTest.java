package com.wizeline.maven.learningjavamaven.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class XmlBeanTest {

    @InjectMocks
    private XmlBean xmlBean;

    @Test
    void getData() {
        xmlBean.getData();

    }

    @Test
    void setData() {
        Object obj = new Object();
        xmlBean.setData(obj);
    }
}