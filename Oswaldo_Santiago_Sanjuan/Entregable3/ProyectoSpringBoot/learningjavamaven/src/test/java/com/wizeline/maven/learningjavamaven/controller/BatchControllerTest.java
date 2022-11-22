package com.wizeline.maven.learningjavamaven.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class BatchControllerTest {
    @InjectMocks
    BatchController batchController;

    @Test
    void startBatch() {
        batchController.startBatch();
    }
}