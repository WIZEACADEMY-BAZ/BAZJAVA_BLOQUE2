package com.baz.wizeline.learningspring.batch;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserProcessorTest {

    @InjectMocks
    UserProcessor processor;

    @Test
    @DisplayName("UserProcessor Batch")
    void process() throws Exception {
        String data = processor.process("data");
        assertNotNull(data);
    }
}