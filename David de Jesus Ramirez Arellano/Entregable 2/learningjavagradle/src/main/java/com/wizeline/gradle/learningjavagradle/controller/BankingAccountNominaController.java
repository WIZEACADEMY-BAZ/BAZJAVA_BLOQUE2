package com.wizeline.gradle.learningjavagradle.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wizeline.gradle.learningjavagradle.model.BankAccountDTO;
import com.wizeline.gradle.learningjavagradle.model.BankAccountNomina;
import com.wizeline.gradle.learningjavagradle.model.ResponseDTO;
import com.wizeline.gradle.learningjavagradle.model.UserDTO;
import com.wizeline.gradle.learningjavagradle.service.BankAccountNominaService;

@RestController
@RequestMapping("/nomina")
public class BankingAccountNominaController {
	 private static final Logger LOGGER = Logger.getLogger(BankingAccountNominaController.class.getName());
	 
	@Autowired
	BankAccountNominaService service;
	
	@PreAuthorize("hasRole('USUARIO')")
    @GetMapping(value = "/getUserAccount", produces = "application/json")
    public ResponseEntity<BankAccountNomina> getUserAccount(@RequestParam String user){
        return service.obtenerCuenta(user);
    }
	@PreAuthorize("hasRole('USUARIO')")
    @PostMapping(value ="/createAccountNomina", produces="application/json", consumes="application/json")
    public  ResponseEntity<?> createAccountNomina(@RequestBody BankAccountNomina request) {
        return service.createNomina(request);
    }
	@PreAuthorize("hasRole('USUARIO')")
    @PutMapping(value="/updateAccountNomina", produces="application/json", consumes="application/json")
    public ResponseEntity<?> updateAccountNomina(@RequestBody BankAccountNomina request){
        return service.updateNomina(request);    	
    }
	@PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping(value="/deleteAccountNomina", produces="application/json")
    public ResponseEntity<?> deleteAccountNomina(@RequestParam Long AccountNumber){
        return service.deleteNomina(AccountNumber);    	
    }
    
}
