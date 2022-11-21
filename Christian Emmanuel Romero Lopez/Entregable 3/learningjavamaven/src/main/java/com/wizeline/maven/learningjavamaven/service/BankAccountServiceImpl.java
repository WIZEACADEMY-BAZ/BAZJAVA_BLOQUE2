package com.wizeline.maven.learningjavamaven.service;

import com.wizeline.maven.learningjavamaven.repository.BankingAccountRepository;
import com.wizeline.maven.learningjavamaven.model.BankAccountModel;
import com.wizeline.maven.learningjavamaven.enums.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.wizeline.maven.learningjavamaven.utils.Utils.getCountry;
import static com.wizeline.maven.learningjavamaven.utils.Utils.pickRandomAccountType;
import static com.wizeline.maven.learningjavamaven.utils.Utils.randomAcountNumber;
import static com.wizeline.maven.learningjavamaven.utils.Utils.randomBalance;
import static com.wizeline.maven.learningjavamaven.utils.Utils.randomInt;

import java.util.logging.Logger;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    private static final Logger LOGGER = Logger.getLogger(BankAccountServiceImpl.class.getName());

    @Autowired
    BankingAccountRepository bankAccountRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<BankAccountModel> getAccounts(){
        List<BankAccountModel> accountDTOList = new ArrayList<>();
        BankAccountModel bankAccountOne = buildBankAccouunt("user3@wizeline.com", true, Country.MX, LocalDateTime.now().minusDays(7));
        accountDTOList.add(bankAccountOne);

        mongoTemplate.save(bankAccountOne);

        BankAccountModel bankAccountTwo = buildBankAccouunt("user1@wizeline.com", false, Country.FR, LocalDateTime.now().minusMonths(2));
        accountDTOList.add(bankAccountTwo);

        mongoTemplate.save(bankAccountTwo);

        BankAccountModel bankAccountThree = buildBankAccouunt("user2@wizeline.com" ,false, Country.US, LocalDateTime.now().minusYears(4));
        accountDTOList.add(bankAccountThree);

        mongoTemplate.save(bankAccountThree);

        mongoTemplate.findAll(BankAccountModel.class).stream().map(bankAccountModel -> bankAccountModel.getUserName()).forEach(
                (user) -> {
                    LOGGER.info("User stored in bankAccountCollection " + user );
                });

        return accountDTOList;
    }

    @Override
    public BankAccountModel getAccountDetails(String user, String lastUsage){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyy");
        LocalDate localDate = LocalDate.parse(lastUsage, dateTimeFormatter);
        return buildBankAccouunt(user, true, Country.MX, localDate.atStartOfDay());
    }

    @Override
    public void deleteAccounts() {
        bankAccountRepository.deleteAll();
    }

    @Override
    public List<BankAccountModel> getAccountByUser(String user) {
        Query query = new Query();
        query.addCriteria(Criteria.where("user").is(user));
        return mongoTemplate.find(query, BankAccountModel.class);
    }

    private BankAccountModel buildBankAccouunt(String user, boolean isActive, Country country, LocalDateTime lastUsage){
        BankAccountModel bankAccountModel = new BankAccountModel();
        bankAccountModel.setAccountNumber(randomAcountNumber());
        bankAccountModel.setAccountName("Dummy Account ".concat(randomInt()));
        bankAccountModel.setUserName(user);
        bankAccountModel.setAccountBalance(randomBalance());
        bankAccountModel.setAccountType(pickRandomAccountType());
        bankAccountModel.setCountry(getCountry(country));
        bankAccountModel.setAccountActive(isActive);
        bankAccountModel.getLastUsage();
        bankAccountModel.setCreationDate(lastUsage);
        bankAccountModel.setAccountActive(isActive);
        return bankAccountModel;
    }

}
