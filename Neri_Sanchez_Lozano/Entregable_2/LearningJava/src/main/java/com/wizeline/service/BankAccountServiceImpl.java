package com.wizeline.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.wizeline.enums.Country;
import com.wizeline.model.BankAccountDTO;
import com.wizeline.repository.BankingAccountRepository;
import com.wizeline.utils.Utils;

@Service
public class BankAccountServiceImpl implements BankAccountService{
	
	private static final Logger log = Logger.getLogger(BankAccountServiceImpl.class.getName());
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Autowired
	BankingAccountRepository bankAccountRepository;

	@Override
	public BankAccountDTO getAccountDetails(String user, String lastUsage) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate usage = LocalDate.parse(lastUsage, dateFormatter);
		return buildBankAccount(user, true, Country.MX, usage.atStartOfDay());
	}

	private BankAccountDTO buildBankAccount(String user, boolean isActive, Country country, LocalDateTime lastUsage) {
		BankAccountDTO bankAccountDTO = new BankAccountDTO();
	    bankAccountDTO.setAccountNumber(Utils.randomAcountNumber());
	    bankAccountDTO.setAccountName("Dummy Account".concat(Utils.randomInt()));
	    bankAccountDTO.setUser(user);
	    bankAccountDTO.setAccountBalance(Utils.randomBalance());
	    bankAccountDTO.setAccountType(Utils.pickRandomAccountType());
	    bankAccountDTO.setCountry(Utils.getCountry(country));
	    bankAccountDTO.setAccountActive(isActive);
	    bankAccountDTO.setLastUsage(lastUsage);
	    return bankAccountDTO;
	}

	@Override
	public List<BankAccountDTO> getAccounts() {
		List<BankAccountDTO> accountDTOList = new ArrayList<>();	    
	    mongoTemplate.findAll(BankAccountDTO.class).stream().map(bankAccountDTO -> bankAccountDTO.getUser()).forEach(
	            (user) -> {
	            	log.info("User stored in bankAccountCollection " + user );
	            });
	    
		return accountDTOList;
	}

	@Override
	public void deleteAccounts() {
		bankAccountRepository.deleteAll();
	}

	@Override
	public List<BankAccountDTO> getAccountByUser(String user) {
		Query query = new Query();
		query.addCriteria(Criteria.where("user").is(user));
		return mongoTemplate.find(query, BankAccountDTO.class);
	}

	@Override
	public void postAccount(BankAccountDTO account) {
	    mongoTemplate.save(account);
		
	}

	@Override
	public void updateAccount(BankAccountDTO account) {
		// TODO Auto-generated method stub
		
	}

	
	

}
