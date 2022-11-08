package com.Wizeline.maven.learningjavamaven.service;

import com.Wizeline.maven.learningjavamaven.LearningjavamavenApplication;
import com.Wizeline.maven.learningjavamaven.model.BankAccountDTO;
import com.Wizeline.maven.learningjavamaven.enums.Country;
import com.Wizeline.maven.learningjavamaven.repository.BankingAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

//import javax.management.Query;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.Wizeline.maven.learningjavamaven.utils.Utils.getCountry;
import static com.Wizeline.maven.learningjavamaven.utils.Utils.pickRandomAccountType;
import static com.Wizeline.maven.learningjavamaven.utils.Utils.randomAccountNumber;
import static com.Wizeline.maven.learningjavamaven.utils.Utils.randomBalance;
import static com.Wizeline.maven.learningjavamaven.utils.Utils.randomInt;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    private static final Logger LOGGER = Logger.getLogger(LearningjavamavenApplication.class.getName());

    @Autowired
    BankingAccountRepository bankingAccountRepository;

    @Autowired
    MongoTemplate mongoTemplate;


    @Override
    public List<BankAccountDTO> getAccounts() {
        //Definicion de lista con la info de la cuentas existentes.
        List<BankAccountDTO> accountDTOList = new ArrayList<>();
        BankAccountDTO bankAccountOne = buildBankAccount("user3@wizeline.com", true, Country.MX,LocalDateTime.now().minusDays(7));
        accountDTOList.add(bankAccountOne);

        //Guardar cada record en la db de mongo(en la coleccion bank AccountCollection)
        mongoTemplate.save(bankAccountOne);

        BankAccountDTO bankAccountTwo = buildBankAccount("user1@wizeline.com", false, Country.FR, LocalDateTime.now().minusMonths(2));
        accountDTOList.add(bankAccountTwo);

        mongoTemplate.save(bankAccountTwo);

        BankAccountDTO bankAccountThree = buildBankAccount("user2@wizeline.com", false, Country.US, LocalDateTime.now().minusYears(4));
        accountDTOList.add(bankAccountThree);

        mongoTemplate.save(bankAccountThree);

        mongoTemplate.findAll(BankAccountDTO.class).stream().map(bankAccountDTO -> bankAccountDTO.getUserName()).forEach(
                (user) -> {
                    LOGGER.info("User stored in bankAccountCollection" + user);
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
        return mongoTemplate.find(query,BankAccountDTO.class);
}

    @Override
    public BankAccountDTO getAccountDetails(String user, String lastUsage) {
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate usage = LocalDate.parse(lastUsage, dateformatter);
        return buildBankAccount(user,true, Country.MX, usage.atStartOfDay());
    }

    //Creación de tipo de dato BankAccount
    private BankAccountDTO buildBankAccount(String user,boolean isActive, Country country, LocalDateTime lastUsage) {
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setAccountNumber(randomAccountNumber());
        bankAccountDTO.setAccountName("Dummy Account".concat(randomInt()));
        bankAccountDTO.setUserName(user);
        bankAccountDTO.setAccountBalance(randomBalance());
        bankAccountDTO.setAccountType(pickRandomAccountType());
        bankAccountDTO.setCountry(getCountry(country));
        bankAccountDTO.getLastUsage();
        bankAccountDTO.setCreationDate(lastUsage);
        bankAccountDTO.setAccountActive(isActive);
        return bankAccountDTO;
    }
}
