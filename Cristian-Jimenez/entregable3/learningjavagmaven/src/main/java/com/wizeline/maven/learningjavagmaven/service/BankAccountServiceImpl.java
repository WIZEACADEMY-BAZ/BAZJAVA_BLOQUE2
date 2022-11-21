package com.wizeline.maven.learningjavagmaven.service;

import com.wizeline.maven.learningjavagmaven.controller.UserController;
import com.wizeline.maven.learningjavagmaven.model.BankAccountModel;
import com.wizeline.maven.learningjavagmaven.enums.AccountType;
import java.util.ArrayList;

import java.util.List;
import com.wizeline.maven.learningjavagmaven.enums.Country;
import com.wizeline.maven.learningjavagmaven.repository.BankingAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

//import javax.management.Query;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;


import static com.wizeline.maven.learningjavagmaven.utils.Utils.getCountry;
import static com.wizeline.maven.learningjavagmaven.utils.Utils.pickRandomAccountType;
import static com.wizeline.maven.learningjavagmaven.utils.Utils.randomAccountNumber;
import static com.wizeline.maven.learningjavagmaven.utils.Utils.randomBalance;
import static com.wizeline.maven.learningjavagmaven.utils.Utils.randomInt;

@Service
public class BankAccountServiceImpl implements BankAccountService {



    // Creación de tipo de dato BankAccount
    private BankAccountModel buildBankAccount(String user, boolean isActive, String lastUsage) {
        BankAccountModel bankAccountModel = new BankAccountModel();
        bankAccountModel.setAccountNumber(123L);
        bankAccountModel.setAccountName("Dummy Account");
        bankAccountModel.setUser(user);
        bankAccountModel.setAccountBalance(843.24);
        bankAccountModel.setAccountType(AccountType.NOMINA);
        bankAccountModel.setCountry("Mexico");
        bankAccountModel.setAccountActive(isActive);
        return bankAccountModel;
    }

    @Autowired
    BankingAccountRepository bankingAccountRepository;


    @Autowired
     MongoTemplate mongoTemplate;

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    @Override
    public List<BankAccountModel> getAccounts() {
        // Definicion de lista con la informacion de las cuentas existentes.
        List<BankAccountModel> accountDTOList = new ArrayList<>();
     //   accountDTOList.add(buildBankAccount("user3@wizeline.com", true, Country.MX, LocalDateTime.now().minusDays(7)));
     //   accountDTOList.add(buildBankAccount("user1@wizeline.com", false, Country.FR, LocalDateTime.now().minusMonths(2)));//  accountDTOList.add(buildBankAccount("user2@wizeline.com" ,false, Country.US, LocalDateTime.now().minusYears(4)));
        BankAccountModel bankAccountOne = buildBankAccount("user3@wizeline.com", true, Country.MX, LocalDateTime.now().minusDays(7));
        accountDTOList.add(bankAccountOne);

        //Guardar cada record en la db de mongo (en la coleccion bankAccountCollection)
        mongoTemplate.save(bankAccountOne);

        BankAccountModel bankAccountTwo = buildBankAccount("user1@wizeline.com", false, Country.FR, LocalDateTime.now().minusMonths(2));
        accountDTOList.add(bankAccountTwo);

        //Guardar cada record en la db de mongo (en la coleccion bankAccountCollection)
        mongoTemplate.save(bankAccountTwo);

        BankAccountModel bankAccountThree = buildBankAccount("user2@wizeline.com" ,false, Country.US, LocalDateTime.now().minusYears(4));
        accountDTOList.add(bankAccountThree);

        //Guardar cada record en la db de mongo (en la coleccion bankAccountCollection)
        mongoTemplate.save(bankAccountThree);

        //Imprime en la Consola cuales son los records encontrados en la coleccion
        //bankAccountCollection de la mongo db
        mongoTemplate.findAll(BankAccountModel.class).stream().map(bankAccountModel -> bankAccountModel.getUser()).forEach(
                (user) -> {
                    LOGGER.info("User stored in bankAccountCollection " + user );

                });

        //Esta es la respuesta que se retorna al Controlador
        //y que sera desplegada cuando se haga la llamada a los
        //REST endpoints que la invocan (un ejemplo es el endpoint de  getAccounts)

        return accountDTOList;
    }

    @Override
    public void deleteAccounts() {
        bankingAccountRepository.deleteAll();
    }

    @Override
    public BankAccountModel getAccountDetails(String user, String lastUsage) {
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate usage = LocalDate.parse(lastUsage, dateformatter);
        return buildBankAccount(user, true, Country.MX, usage.atStartOfDay()); // Agregar código de pais, agregar fecha y hora
    }

    // Creación de tipo de dato BankAccount con la ayuda de la clase Utils.java
    private BankAccountModel buildBankAccount(String user, boolean isActive, Country country, LocalDateTime lastUsage) {
        BankAccountModel bankAccountModel = new BankAccountModel();
        bankAccountModel.setAccountNumber(randomAccountNumber());
        bankAccountModel.setAccountName("Dummy Account ".concat(randomInt()));
        bankAccountModel.setUser(user);
        bankAccountModel.setAccountBalance(randomBalance());
        bankAccountModel.setAccountType(pickRandomAccountType());
        bankAccountModel.setCountry(getCountry(country));
        bankAccountModel.getLastUsage();
        bankAccountModel.setCreationDate(lastUsage);
        bankAccountModel.setAccountActive(isActive);
        return bankAccountModel;
    }

    @Override
    public List<BankAccountModel> getAccountByUser(String user) {
        //Buscamos todos aquellos registros de tipo BankAccountDTO
        //que cumplen con la criteria de que el userName haga match
        //con la variable user

        Query query = new Query();
        query.addCriteria(Criteria.where("userName").is(user));
        return mongoTemplate.find(query, BankAccountModel.class);
  //                return null;
    }




}