package com.wizeline.baz.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.wizeline.baz.enums.ResponseStatus;
import com.wizeline.baz.exceptions.UserNotFoundException;
import com.wizeline.baz.model.BankAccountDTO;
import com.wizeline.baz.model.ErrorDTO;
import com.wizeline.baz.model.request.CreateAccountRequest;
import com.wizeline.baz.model.response.BankAccountDetailsResponse;
import com.wizeline.baz.model.response.BaseResponseDTO;
import com.wizeline.baz.model.response.CreateBankAccountResponseDTO;
import com.wizeline.baz.model.response.GetAccountsResponse;
import com.wizeline.baz.repository.BankAccountFactory;
import com.wizeline.baz.repository.BankAccountFactoryImp;
import com.wizeline.baz.repository.BankAccountRepository;
import com.wizeline.baz.repository.UserRepository;
import com.wizeline.baz.utils.StatusCodes;

@Service
public class BankAccountServiceImpl implements BankAccountService {

	@Autowired
	private BankAccountRepository bankAccountRepository;

	@Autowired
	private UserRepository userRepository;
	
	private BankAccountFactory bankAccountFactory = BankAccountFactoryImp.getInstance();
	
	
	@Override
	public ResponseEntity<BaseResponseDTO> createAccount(CreateAccountRequest request) {		
		boolean existsUser = userRepository.existsById(request.getUserId());
		if(!existsUser)
			throw new UserNotFoundException(request.getUserId());
		BankAccountDTO bankAccount = bankAccountFactory.createBankAccount(request.getUserId(), request.getAccountType());
		bankAccountRepository.insert(bankAccount);
		CreateBankAccountResponseDTO response = new CreateBankAccountResponseDTO();
		BeanUtils.copyProperties(bankAccount, response);
		response.makeSuccess();
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	
	@Override
	public ResponseEntity<BaseResponseDTO> getAccountDetails(long account) {
		Optional<BankAccountDTO> bankAccountOpt = bankAccountRepository.findById(account);
		if(!bankAccountOpt.isPresent()) {
			ErrorDTO error = new ErrorDTO(StatusCodes.BANK_ACCOUNT_DOESNT_EXIST, "BankAccount -> " +  account);
			return new ResponseEntity<>(BaseResponseDTO.builder()
											.status(ResponseStatus.FAILED)
											.code(StatusCodes.FAILED)
											.errors(error).build(),
										HttpStatus.NOT_FOUND);
		}
		BankAccountDetailsResponse response = new BankAccountDetailsResponse();
		BeanUtils.copyProperties(bankAccountOpt.get(), response);
		response.makeSuccess();
		return ResponseEntity.ok(response);
	}

	
	@Override
	public ResponseEntity<BaseResponseDTO> deleteAccount(long account) {
		bankAccountRepository.deleteById(account);
		BaseResponseDTO response = BaseResponseDTO.builder().build();
		response.makeSuccess();
		return ResponseEntity.ok(response);
	}
	
	@Override
	public ResponseEntity<BaseResponseDTO> getAccounts() {
		List<BankAccountDTO> bankAccounts = bankAccountRepository.findAll();
		GetAccountsResponse response = new GetAccountsResponse();
		response.setBankAccounts(bankAccounts);
		response.makeSuccess();
		return ResponseEntity.ok(response);
	}
	
	
}
