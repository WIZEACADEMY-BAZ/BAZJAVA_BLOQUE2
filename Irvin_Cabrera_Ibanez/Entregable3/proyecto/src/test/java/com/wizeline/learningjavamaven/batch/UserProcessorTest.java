package com.wizeline.learningjavamaven.batch;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserProcessorTest {

  @InjectMocks
  UserProcessor userProcessor;

  @Test
  void process() {
    String data = userProcessor.process("date");
    assertNotNull(data);
  }
}