package com.wizeline.learningspring.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wizeline.learningspring.model.herencia.UserBreaches;

import java.util.List;

public interface UserBreachesService {
    List<UserBreaches> breaches() throws JsonProcessingException;
}
