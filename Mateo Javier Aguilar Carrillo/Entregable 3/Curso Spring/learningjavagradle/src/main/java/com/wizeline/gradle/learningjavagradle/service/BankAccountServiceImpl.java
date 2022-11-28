package com.wizeline.gradle.learningjavagradle.service;

import static com.wizeline.gradle.learningjavagradle.utils.Utils.getCountry;
import static com.wizeline.gradle.learningjavagradle.utils.Utils.pickRandomAccountType;
import static com.wizeline.gradle.learningjavagradle.utils.Utils.randomAcountNumber;
import static com.wizeline.gradle.learningjavagradle.utils.Utils.randomBalance;
import static com.wizeline.gradle.learningjavagradle.utils.Utils.randomInt;

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

import com.wizeline.gradle.learningjavagradle.enums.Country;
import com.wizeline.gradle.learningjavagradle.model.BankAccountDTO;
import com.wizeline.gradle.learningjavagradle.repository.BankingAccountRepository;

@Service
public class BankAccountServiceImpl implements BankAccountService{
	
	@Autowired
	BankingAccountRepository bankingAccountRepository;
	
	@Autowired
	MongoTemplate template;
	
	private static final Logger LOGGER = Logger.getLogger(BankAccountServiceImpl.class.getName());

	@Override
    public List<BankAccountDTO> getAccounts() {
        List<BankAccountDTO> accountDTOList = new ArrayList<>();
        BankAccountDTO bankAccountOne = buildBankAccount("user3@wizeline.com", true, Country.MX, LocalDateTime.now().minusDays(7));
        accountDTOList.add(bankAccountOne);
        template.save(bankAccountOne);
        
        BankAccountDTO bankAccountTwo = buildBankAccount("user1@wizeline.com", false, Country.FR, LocalDateTime.now().minusMonths(2));
        accountDTOList.add(bankAccountTwo);
        template.save(bankAccountTwo);
        
        BankAccountDTO bankAccountThree = buildBankAccount("user2@wizeline.com" ,false, Country.US, LocalDateTime.now().minusYears(4));
        accountDTOList.add(bankAccountThree);
        template.save(bankAccountThree);
        
        template.findAll(BankAccountDTO.class).stream().map(bankAccountDTO -> bankAccountDTO.getUserName()).forEach(
                user -> {
                        LOGGER.info("User stored in bankAccountCollection " + user );
                });
        
        return accountDTOList;
    }

	@Override
	public void deleteAccounts() {
		bankingAccountRepository.deleteAll();
	}

	@Override
	public List<BankAccountDTO> getAccountByUser(String user) {
		Query query = new Query();
		query.addCriteria(Criteria.where("userName").is(user));
		return template.find(query, BankAccountDTO.class);
	}
	
	@Override
    public BankAccountDTO getAccountDetails(String user, String lastUsage) {
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate usage = LocalDate.parse(lastUsage, dateformatter);
        return buildBankAccount(user, true, Country.MX, usage.atStartOfDay());
    }

	@Override
	public BankAccountDTO buildBankAccount(String user, boolean isActive, Country country, LocalDateTime lastUsage) {
		BankAccountDTO bankAccountDTO = new BankAccountDTO();
		bankAccountDTO.setAccountNumber(randomAcountNumber());
		bankAccountDTO.setAccountName("Dummy Account ".concat(randomInt()));
		bankAccountDTO.setUserName(user);
		bankAccountDTO.setAccountBalance(randomBalance());
		bankAccountDTO.setAccountType(pickRandomAccountType());
		bankAccountDTO.setCountry(getCountry(country));
		bankAccountDTO.setCreationDate(lastUsage);
		bankAccountDTO.setAccountActive(isActive);
		return bankAccountDTO;
	}

}
