package com.wizeline.learningjavamaven.batch;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserReaderTest {

  @InjectMocks
  UserReader userReader;

  @Test
  void read() {
    String read = userReader.read();
    assertNotNull(read);
  }
}