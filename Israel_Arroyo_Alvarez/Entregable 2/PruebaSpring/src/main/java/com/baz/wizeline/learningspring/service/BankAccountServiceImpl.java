package com.baz.wizeline.learningspring.service;


import com.baz.wizeline.learningspring.LearningspringApplication;
import com.baz.wizeline.learningspring.enums.Country;
import com.baz.wizeline.learningspring.model.BankAccountDTO;
import com.baz.wizeline.learningspring.repository.BankingAccountRepository;
import com.baz.wizeline.learningspring.utils.GenerateAccounts;
import com.baz.wizeline.learningspring.utils.Utils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@Service
public class BankAccountServiceImpl implements BankAccountService{

    private static final Logger LOGGER = Logger.getLogger(LearningspringApplication.class.getName());


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

        //Verificar Optional
        BankAccountDTO bankAccountOptional = buildBankAccount(null ,false, Country.US, LocalDateTime.now().minusYears(4));
        Optional<String> re = Utils.nombreCuentaOptional(bankAccountOptional);
        bankAccountOptional.setUserName(re.get());
        accountDTOList.add(bankAccountOptional);



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

    public List<BankAccountDTO> getAccountsActives() {

        List<BankAccountDTO> accountActives = getAccounts();

        accountActives = accountActives.stream().filter( active -> active.isAccountActive()==true).collect(Collectors.toList());


        accountActives = accountActives.stream().peek(w->w.setAccountName("Uso de PEEK ") ).collect(Collectors.toList());





        return accountActives;


    }

    @Override
    public List<BankAccountDTO> accountsEncrypt() {
        List<BankAccountDTO> cuentasObtenidas = getAccounts();

        byte[] keyBytes = new byte[]{
                0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef
        };
        byte[] ivBytes = new byte[]{
                0x00, 0x01, 0x02, 0x03, 0x00, 0x00, 0x00, 0x01
        };

        Security.addProvider(new BouncyCastleProvider());

        SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        Cipher cipher = null;

        try {
            cipher = Cipher.getInstance("DES/CTR/NoPadding", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
            // Cifraremos solamente el nombre y el country (pueden cifrar todos los parámetros que gusten)
            for (BankAccountDTO cuentasObtenida : cuentasObtenidas) {
                String accountName = cuentasObtenida.getAccountName();
                byte[] arrAccountName = accountName.getBytes();
                byte[] accountNameCipher = new byte[cipher.getOutputSize(arrAccountName.length)];
                int ctAccountNameLength = cipher.update(arrAccountName, 0, arrAccountName.length, accountNameCipher, 0);
                ctAccountNameLength += cipher.doFinal(accountNameCipher, ctAccountNameLength);
                cuentasObtenida.setAccountName(String.valueOf(accountNameCipher));

                String accountCountry = cuentasObtenida.getCountry();
                byte[] arrAccountCountry = accountCountry.getBytes();
                byte[] accountCountryCipher = new byte[cipher.getOutputSize(arrAccountCountry.length)];
                int ctAccountCountryLength = cipher.update(arrAccountCountry, 0, arrAccountCountry.length, accountCountryCipher, 0);
                ctAccountNameLength += cipher.doFinal(accountCountryCipher, ctAccountCountryLength);
                cuentasObtenida.setCountry(accountCountryCipher.toString());

            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        } catch (ShortBufferException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        return cuentasObtenidas;



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
        bankAccountDTO.setUserName(user);
        bankAccountDTO.setAccountBalance(Utils.randomBalance());
        bankAccountDTO.setAccountType(Utils.pickRandomAccountType());
        bankAccountDTO.setCountry(Utils.getCountry(country));
        bankAccountDTO.getLastUsage();
        bankAccountDTO.setCreationDate(lastUsage);
        bankAccountDTO.setAccountActive(isActive);
        return bankAccountDTO;
    }

    @Override
    public void deleteAccounts() {
        //Deleting all records inside of bankAccountCollection in the mongo db
        bankAccountRepository.deleteAll();
    }

    @Override
    public List<BankAccountDTO> getAccountByUser(String user) {
        //Buscamos todos aquellos registros de tipo BankAccountDTO
        //que cumplen con la criteria de que el userName haga match
        //con la variable user
        Query query = new Query();
        query.addCriteria(Criteria.where("userName").is(user));
        return mongoTemplate.find(query, BankAccountDTO.class);
    }



    // Creación de tipo de dato BankAccount
    private  GenerateAccounts dummysAccount() {
        List<BankAccountDTO> cuentas = new ArrayList<>();
        GenerateAccounts dummysAccounts = () -> {


            Runnable task = () -> {
                String threadName = Thread.currentThread().getName();
                System.out.println(threadName);
                BankAccountDTO cuenta = buildBankAccount(threadName,true,Country.MX,LocalDateTime.now());
                cuentas.add(cuenta);
            };

            Thread thread = new Thread(task,"Hilo1");
            Thread thread2 = new Thread(task,"Hilo2");

            thread.start();
            thread2.start();


            return cuentas;
        };

        return dummysAccounts;

    }

    public List<BankAccountDTO> getAccountsFunctional() {
        // Definicion de lista con la informacion de las cuentas existentes.
        GenerateAccounts generateAccounts = dummysAccount();
        return generateAccounts.generateDummysccounts();
    }




}
