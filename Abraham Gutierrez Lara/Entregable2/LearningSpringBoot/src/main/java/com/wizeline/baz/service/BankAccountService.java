package com.wizeline.baz.service;

import org.springframework.http.ResponseEntity;

import com.wizeline.baz.model.request.CreateAccountRequest;
import com.wizeline.baz.model.response.BaseResponseDTO;

public interface BankAccountService {
	ResponseEntity<BaseResponseDTO> getAccounts();
	ResponseEntity<BaseResponseDTO> createAccount(CreateAccountRequest request);
	ResponseEntity<BaseResponseDTO> getAccountDetails(long account);
	ResponseEntity<BaseResponseDTO> deleteAccount(long account);
}
