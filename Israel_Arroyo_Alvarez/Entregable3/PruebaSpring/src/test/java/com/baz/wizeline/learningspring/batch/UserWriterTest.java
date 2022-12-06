package com.baz.wizeline.learningspring.batch;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserWriterTest {

    @InjectMocks
    UserWriter writer;

    @Test
    @DisplayName("Writer Batch")
    void write() throws Exception {
        List<String> data = new ArrayList<>();
        data.add("valor");
        writer.write(data);
    }
}