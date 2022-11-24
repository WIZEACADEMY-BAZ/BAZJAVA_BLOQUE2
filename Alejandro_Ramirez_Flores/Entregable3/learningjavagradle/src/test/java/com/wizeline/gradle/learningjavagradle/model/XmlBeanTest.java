package com.wizeline.gradle.learningjavagradle.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class XmlBeanTest {
	
	    @InjectMocks
	    private XmlBean xmlBean;

	@Test
	void testGetData() {
		 xmlBean.getData();
	}

	@Test
	void testSetData() {
		Object obj = new Object();
        xmlBean.setData(obj);
	}

}
