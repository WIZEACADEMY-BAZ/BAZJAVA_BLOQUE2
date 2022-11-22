package com.wizeline.learningjavamaven.batch;

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
  UserWriter userWriter;

  @Test
  void write() {
    List<String> data = new ArrayList<>();
    data.add("valor");
    userWriter.write(data);
  }
}