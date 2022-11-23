package com.wizeline.baz.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.wizeline.baz.enums.BankAccountType;
import com.wizeline.baz.enums.Country;
import com.wizeline.baz.enums.DebitCardType;
import com.wizeline.baz.model.BankAccountDTO;
import com.wizeline.baz.model.DebitCard;

public class BankAccountFactoryImp implements BankAccountFactory {
	
	private static BankAccountFactoryImp instance;
	private static final double ACCOUNT_INITIAL_BONUS = 100.0;
	
	private BankAccountFactoryImp() {
		
	}
	
	public static BankAccountFactoryImp getInstance() {
		if(instance == null)
			instance = new BankAccountFactoryImp();
		return instance;
	}

	@Override
	public BankAccountDTO createBankAccount(String userId, BankAccountType accountType) {
		DebitCard card = null;
		BankAccountDTO bankAccount = generateBankAccount(userId, accountType);
		switch (accountType) {
		case PLATINUM:
			card = generateDebitCard(DebitCardType.MASTERCARD);
			bankAccount.setAccountBalance(ACCOUNT_INITIAL_BONUS);
			break;
		case NOMINA:
			card = generateDebitCard(DebitCardType.MASTERCARD);
			break;
		default:
			card = generateDebitCard(DebitCardType.VISA);
			break;
		}
		bankAccount.setCard(card);
		return bankAccount;
	}
	
	private BankAccountDTO generateBankAccount(String userId, BankAccountType accountType) {
		BankAccountDTO bankAccount = new BankAccountDTO();
		bankAccount.setAccountNumber(System.currentTimeMillis());
		bankAccount.setUserId(userId);
		bankAccount.setAccountAlias(accountType.toString());
		bankAccount.setAccountType(accountType);
		bankAccount.setCountry(Country.MX);
		bankAccount.setActive(true);
		bankAccount.setCreationDate(LocalDateTime.now());
		return bankAccount;
	}
	
	private DebitCard generateDebitCard(DebitCardType type) {
		return  new DebitCard(System.currentTimeMillis(), 
				 LocalDate.now().plusYears(5), 0, type);
	}
	
}
