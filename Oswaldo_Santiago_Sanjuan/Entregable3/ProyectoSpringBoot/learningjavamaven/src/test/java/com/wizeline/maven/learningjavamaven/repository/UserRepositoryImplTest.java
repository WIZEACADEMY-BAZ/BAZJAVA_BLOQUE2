package com.wizeline.maven.learningjavamaven.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserRepositoryImplTest {
    @InjectMocks
    private UserRepository userRepository;

    @Test
    void createUser() {

    }

    @Test
    void login() {

    }
}