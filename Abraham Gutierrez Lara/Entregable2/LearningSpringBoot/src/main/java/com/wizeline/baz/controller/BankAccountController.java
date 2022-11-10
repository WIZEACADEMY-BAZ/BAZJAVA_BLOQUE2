package com.wizeline.baz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wizeline.baz.model.request.CreateAccountRequest;
import com.wizeline.baz.model.response.BaseResponseDTO;
import com.wizeline.baz.service.BankAccountService;

@RequestMapping("/bank-account")
@RestController
public class BankAccountController {
	
	@Autowired
	private BankAccountService bankAccountService;
	
	@PreAuthorize("hasRole('BANKER')")
	@PostMapping
	public ResponseEntity<BaseResponseDTO> createBankAccount(@RequestBody CreateAccountRequest request) {
		return bankAccountService.createAccount(request);
	}
	
	@PreAuthorize("hasRole('USER') or hasRole('BANKER')" )
	@GetMapping("/{account}")
	public ResponseEntity<BaseResponseDTO> getAccountDetails(@PathVariable long account) {
		return bankAccountService.getAccountDetails(account);
	}
	
	@PreAuthorize(value = "hasRole('BANKER')")
	@GetMapping
	public ResponseEntity<BaseResponseDTO> getAccounts() {
		return bankAccountService.getAccounts();
	}
	
	@PreAuthorize("hasRole('BANKER')")
	@DeleteMapping("/{account}")
	public ResponseEntity<BaseResponseDTO> deleteAccount(@PathVariable long account) {
		return bankAccountService.deleteAccount(account);
	}
}
