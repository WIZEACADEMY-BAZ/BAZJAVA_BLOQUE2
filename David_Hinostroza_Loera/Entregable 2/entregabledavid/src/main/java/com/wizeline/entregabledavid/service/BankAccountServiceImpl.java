package com.wizeline.entregabledavid.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.wizeline.entregabledavid.EntregabledavidApplication;
import com.wizeline.entregabledavid.enums.Country;
import com.wizeline.entregabledavid.model.BankAccountDTO;
import com.wizeline.entregabledavid.repository.BankingAccountRepository;

import static com.wizeline.entregabledavid.utils.Utils.*;
import static com.wizeline.entregabledavid.utils.Utils.randomBalance;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    private static final Logger LOGGER = Logger.getLogger(EntregabledavidApplication.class.getName());

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
        mongoTemplate.findAll(BankAccountDTO.class).stream().map(bankAccountDTO -> bankAccountDTO.getUser()).forEach(
                (user) -> {
                    LOGGER.info("User stored in bankAccountCollection " + user );
                });

        //Esta es la respuesta que se retorna al Controlador
        //y que sera desplegada cuando se haga la llamada a los
        //REST endpoints que la invocan (un ejemplo es el endpoint de  getAccounts)
        return accountDTOList;
    }

    @Override
    public BankAccountDTO getAccountDetails(String user, String lastUsage) {
        DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate usage = LocalDate.parse(lastUsage, dateformatter);
        return buildBankAccount(user, true, Country.MX, usage.atStartOfDay());
    }

    @Override
    public void deleteAccounts() {
        //Borrar todos los records que esten dentro de la coleccion bankAccountCollection en mongo db.
        bankAccountRepository.deleteAll();
    }

    @Override
    public List<BankAccountDTO> getAccountByUser(String user) {
        //Buscamos todos aquellos registros de tipo BankAccountDTO
        //que cumplen con la criteria de que el userName haga match
        //con la variable user
        Query query = new Query();
        query.addCriteria(Criteria.where("user").is(user));
        return mongoTemplate.find(query, BankAccountDTO.class);
    }

    @Override
    public BankAccountDTO putAccountByUser(String user) {
        //Buscamos el registro de tipo BankAccountDTO
        //que cumple con la criteria de que el userName haga match
        //con la variable user para actualizar accountBalance
        Query query = new Query();
        query.addCriteria(Criteria.where("user").is(user));

        BankAccountDTO bankAccountDTO = mongoTemplate.findOne(query, BankAccountDTO.class);
        Optional<BankAccountDTO> bankAccountDTOOptional = Optional.ofNullable(bankAccountDTO);
        if (bankAccountDTOOptional.isPresent()) {
            LOGGER.info("Balance anterior: " + bankAccountDTO.getAccountBalance());
            bankAccountDTO.setAccountBalance(randomBalance());
            LOGGER.info("Balance actualizado: " + bankAccountDTO.getAccountBalance());
            Update update = new Update();
            update.set("accountBalance", bankAccountDTO.getAccountBalance());

            mongoTemplate.updateFirst(query, update, BankAccountDTO.class);

            return mongoTemplate.findOne(query, BankAccountDTO.class);
        }
        else {
            LOGGER.info("bankAccountDTO " + bankAccountDTO);
            return bankAccountDTO;
        }
    }

    // Creación de tipo de dato BankAccount
    private BankAccountDTO buildBankAccount(String user, boolean isActive, Country country, LocalDateTime lastUsage) {
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setAccountNumber(randomAcountNumber());
        bankAccountDTO.setAccountName("Dummy Account ".concat(randomInt()));
        bankAccountDTO.setUser(user);
        bankAccountDTO.setAccountBalance(randomBalance());
        bankAccountDTO.setAccountType(pickRandomAccountType());
        bankAccountDTO.setCountry(getCountry(country));
        bankAccountDTO.getLastUsage(); // <- Se invoca al metodo de acceso get() para obtener la fecha actual
        bankAccountDTO.setCreationDate(lastUsage);
        bankAccountDTO.setAccountActive(isActive);
        return bankAccountDTO;
    }
}
