package com.wizeline.maven.learninjavamaven.service;

import com.wizeline.maven.learninjavamaven.enums.Country;
import com.wizeline.maven.learninjavamaven.model.BankAccountDTO;
import com.wizeline.maven.learninjavamaven.repository.BankingAccountRepository;
import com.wizeline.maven.learninjavamaven.utils.Utils;
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
import java.util.logging.Logger;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    private static final java.util.logging.Logger LOGGER = Logger.getLogger(BankAccountServiceImpl.class.getName());

    @Autowired
    private BankingAccountRepository bankingAccountRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private BankAccountDTO buildBankAccount(String user, boolean isActive, Country country, LocalDateTime lastUsage){
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setAccountNumber(Utils.randomAccountNumber());
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
    public List<BankAccountDTO> getAccounts() {
        //Definicion de lista con la informacion de las cuentas existentes.
        List<BankAccountDTO> accountDTOList = new ArrayList<>();
        BankAccountDTO bankAccountOne = buildBankAccount("user3@wizeline.com", true, Country.MX, LocalDateTime.now().minusDays(7));
        accountDTOList.add(bankAccountOne);

        //Se guarda cada record en la db de mongo (en la coleccion bankAccountCollection)
        mongoTemplate.save(bankAccountOne);

        BankAccountDTO bankAccountOTwo = buildBankAccount("user3@wizeline.com", true, Country.MX, LocalDateTime.now().minusDays(7));
        accountDTOList.add(bankAccountOTwo);

        //Se guarda cada record en la db de mongo| (en la coleccion bankAccountCollection)
        mongoTemplate.save(bankAccountOTwo);

        BankAccountDTO bankAccountThree = buildBankAccount("user3@wizeline.com", false, Country.US, LocalDateTime.now().minusYears(4));
        accountDTOList.add(bankAccountThree);

        //Se guarda cada record en la db de mongo (en la coleccion bankAccountCollection)
        mongoTemplate.save(bankAccountThree);

        //Se imprime en la consola cuales son los records encontrados en la coleccion
        //bankAccountCollection de la mongo db
        mongoTemplate.findAll(BankAccountDTO.class).stream().map(bankAccountDTO -> bankAccountDTO.getUser())
                .forEach((user) ->{
                    LOGGER.info("User stored in bankAccountCollection " + user );
                });

        //Esta es la respuesta que se retorna al Controlador
        //y que sera desplegada cuando se haga la llamada a los
        //REST endpoints que la invocan (un ejemplo es el endpoint de  getAccounts)
        return accountDTOList;
    }

    @Override
    public List<BankAccountDTO> getAccountByUser(String user) {
        /*
        Buscamos todos aquellos registros de tipo BankAccountDTO
        que cumplen con la criteria de que el userName haga match
        con la variable user
        */
        Query query = new Query();
        query.addCriteria(Criteria.where("user").is(user));
        return mongoTemplate.find(query, BankAccountDTO.class);
    }

    @Override
    public BankAccountDTO findByUser(String user) {
        Query query = new Query();
        query.addCriteria(Criteria.where("user").is(user));
        return mongoTemplate.findById(query, BankAccountDTO.class);
    }

    @Override
    public BankAccountDTO save(BankAccountDTO bankAccountDTO) {
        mongoTemplate.save(bankAccountDTO);
        return bankAccountDTO;
    }

    @Override
    public BankAccountDTO getAccountDetails(String user, String lastUsage){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate usage = LocalDate.parse(lastUsage, dateTimeFormatter);
        return buildBankAccount(user, true, Country.MX, usage.atStartOfDay());
    }

    @Override
    public void deleteAccounts() {
        //Deleting all records inside bankAccountCollection in the mongo db
        bankingAccountRepository.deleteAll();
    }

    /*
    private BankAccountDTO buildBankAccount(String user, boolean isActive, Country country, String lastUsage){
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setAccountNumber(Utils.randomAccountNumber());
        bankAccountDTO.setAccountName("Dummy Account ".concat(Utils.randomInt()));
        bankAccountDTO.setUser(user);
        bankAccountDTO.setAccountBalance(Utils.randomBalance());
        bankAccountDTO.setAccountType(Utils.pickRandomAccountType());
        bankAccountDTO.setCountry(lastUsage);
        bankAccountDTO.setLastUsage(lastUsage);
        bankAccountDTO.setAccountActive(isActive);
        return bankAccountDTO;
    }*/

}
