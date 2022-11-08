package com.wizeline.gradle.learningjavagradle.service;
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
import com.wizeline.gradle.learningjavagradle.utils.Utils;

@Service
public class BankAccountServiceImpl implements BankAccountService {
	
	private static final Logger LOGGER = Logger.getLogger(BankAccountServiceImpl.class.getName());
	
	@Autowired
    BankingAccountRepository bankAccountRepository;

    @Autowired
    MongoTemplate mongoTemplate; 
    
    @Override
    public List<BankAccountDTO> getAccounts() {
        List<BankAccountDTO> accountDTOList = new ArrayList<>();
        BankAccountDTO bankAccountOne = buildBankAccount("user3@wizeline.com", true, Country.MX, LocalDateTime.now().minusDays(7));
        accountDTOList.add(bankAccountOne);
        mongoTemplate.save(bankAccountOne);
        
        BankAccountDTO bankAccountTwo = buildBankAccount("user1@wizeline.com", false, Country.FR, LocalDateTime.now().minusMonths(2));
        accountDTOList.add(bankAccountTwo);
        mongoTemplate.save(bankAccountTwo);

        BankAccountDTO bankAccountThree = buildBankAccount("user2@wizeline.com" ,false, Country.US, LocalDateTime.now().minusYears(4));
        accountDTOList.add(bankAccountThree);
        mongoTemplate.save(bankAccountThree);

        mongoTemplate.findAll(BankAccountDTO.class).stream().map(bankAccountDTO -> bankAccountDTO.getAccountName()).forEach(
                (user) -> {
                    LOGGER.info("User stored in bankAccountCollection " + user );
                });
        return accountDTOList;
    }
    
    @Override
    public void deleteAccounts() {
        bankAccountRepository.deleteAll();
    }

    @Override
    public BankAccountDTO getAccountDetails(String user, String lastUsage) {
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate usage = LocalDate.parse(lastUsage, dateformatter);
        return buildBankAccount(user, true, Country.MX, usage.atStartOfDay());
    }

    // Creación de tipo de dato BankAccount
    private BankAccountDTO buildBankAccount(String user, boolean isActive, Country country, LocalDateTime lastUsage) {
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setAccountNumber(Utils.randomAcountNumber());
        bankAccountDTO.setAccountName("Dummy Account ".concat(Utils.randomInt()));
        bankAccountDTO.setUser(user);
        bankAccountDTO.setAccountBalance(Utils.randomBalance());
        bankAccountDTO.setAccountType(Utils.pickRandomAccountType());
        bankAccountDTO.setCountry(Utils.getCountry(country));
        bankAccountDTO.getLastUsage();
        bankAccountDTO.setCreationDate(lastUsage);
        bankAccountDTO.setAccountActive(isActive);
        return bankAccountDTO;
    }
    
    @Override
    public List<BankAccountDTO> getAccountByUser(String user) {
        Query query = new Query();
        query.addCriteria(Criteria.where("user").is(user));
        return mongoTemplate.find(query, BankAccountDTO.class);
    }
}
