package com.wizeline.maven.LearningJava.service;

import com.wizeline.maven.LearningJava.LearningJavaApplication;
import com.wizeline.maven.LearningJava.enums.Country;
import com.wizeline.maven.LearningJava.model.BankAccountDTO;
import com.wizeline.maven.LearningJava.repository.BankingAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static com.wizeline.maven.LearningJava.utils.Utils.*;

public class BankAccountServiceImpl implements BankAccountService {

    private static final Logger LOGGER = Logger.getLogger(LearningJavaApplication.class.getName());

    @Autowired
    BankingAccountRepository bankAccountRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<BankAccountDTO> getAccounts() {
        // Definicion de lista con la informacion de las cuentas existentes.
        List<BankAccountDTO> accountDTOList = new ArrayList<>();
        BankAccountDTO bankAccountOne = buildBankAccount("user3@wizeline.com", true, Country.MX, LocalDateTime.now().minusDays(7));
        accountDTOList.add(bankAccountOne);

        //Guardar cada record en la db de mongo (en la coleccion bankAccountCollection)
        mongoTemplate.save(bankAccountOne);

        BankAccountDTO bankAccountTwo = buildBankAccount("user1@wizeline.com", false, Country.FR, LocalDateTime.now().minusMonths(2));
        accountDTOList.add(bankAccountTwo);

        //Guardar cada record en la db de mongo (en la coleccion bankAccountCollection)
        mongoTemplate.save(bankAccountTwo);

        BankAccountDTO bankAccountThree = buildBankAccount("user2@wizeline.com" ,false, Country.US, LocalDateTime.now().minusYears(4));
        accountDTOList.add(bankAccountThree);

        //Guardar cada record en la db de mongo (en la coleccion bankAccountCollection)
        mongoTemplate.save(bankAccountThree);

        //Imprime en la Consola cuales son los records encontrados en la coleccion
        //bankAccountCollection de la mongo db
        mongoTemplate.findAll(BankAccountDTO.class).stream().map(bankAccountDTO -> bankAccountDTO.getUserName()).forEach(
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
        //Borrar todos los records que esten dentro de la coleccion bankAccountCollection en mongo db.
        bankAccountRepository.deleteAll();
    }

    @Override
    public List<BankAccountDTO> getAccountByUser(Optional <String> user) {
        //Buscamos todos aquellos registros de tipo BankAccountDTO
        //que cumplen con la criteria de que el userName haga match
        //con la variable user
        if (user.isEmpty()){
            user = Optional.ofNullable("user3@wizeline.com");
        }

        Query query = new Query();
        query.addCriteria(Criteria.where("userName").is(user.get()));
        return mongoTemplate.find(query, BankAccountDTO.class);
    }

    @Override
    public List<BankAccountDTO> updateUserAccounts(String oldUser, String newUser) {
        Query query = Query.query(Criteria.where("userName").is(oldUser));
        mongoTemplate.updateMulti(query, Update.update("userName", newUser), BankAccountDTO.class);
        return mongoTemplate.findAll(BankAccountDTO.class);
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
        bankAccountDTO.setAccountNumber(randomAcountNumber());
        bankAccountDTO.setAccountName("Dummy Account ".concat(randomInt()));
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