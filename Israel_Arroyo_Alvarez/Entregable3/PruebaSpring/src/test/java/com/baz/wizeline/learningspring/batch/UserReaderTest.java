package com.baz.wizeline.learningspring.batch;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserReaderTest {

    @InjectMocks
    UserReader reader;

    @Test
    @DisplayName("Reader Batch")
    void read() throws Exception {
        String read = reader.read();
        assertNotNull(read);
    }
}