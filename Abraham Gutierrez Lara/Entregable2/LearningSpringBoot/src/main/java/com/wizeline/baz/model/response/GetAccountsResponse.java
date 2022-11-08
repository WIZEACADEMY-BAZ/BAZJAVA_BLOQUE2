package com.wizeline.baz.model.response;

import java.util.List;

import com.wizeline.baz.model.BankAccountDTO;

public class GetAccountsResponse extends BaseResponseDTO {
	private List<BankAccountDTO> bankAccounts;

	public List<BankAccountDTO> getBankAccounts() {
		return bankAccounts;
	}
	public void setBankAccounts(List<BankAccountDTO> bankAccounts) {
		this.bankAccounts = bankAccounts;
	}
	
	
}
