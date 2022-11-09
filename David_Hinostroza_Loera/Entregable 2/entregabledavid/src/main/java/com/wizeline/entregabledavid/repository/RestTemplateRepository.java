package com.wizeline.entregabledavid.repository;

import org.springframework.http.ResponseEntity;

public interface RestTemplateRepository {
    ResponseEntity<String> getDatosRestAPI();
}
