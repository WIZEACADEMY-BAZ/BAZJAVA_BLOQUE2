package com.wizeline.learningjavamaven.service;

import com.wizeline.learningjavamaven.enums.Country;
import com.wizeline.learningjavamaven.model.AccountInsertDTO;
import com.wizeline.learningjavamaven.model.BankAccountDTO;
import com.wizeline.learningjavamaven.model.UserDTO;
import com.wizeline.learningjavamaven.repository.BankingAccountRepository;
import com.wizeline.learningjavamaven.utils.Utils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class BankAccountServiceImpl implements BankAccountService {

  private static final Logger LOGGER = Logger.getLogger(BankAccountServiceImpl.class.getName());

  @Autowired
  BankingAccountRepository bankAccountRepository;

  @Autowired
  UserService userService;

  @Autowired
  MongoTemplate mongoTemplate;

  @Override
  public List<BankAccountDTO> getAccounts() {
    List<BankAccountDTO> accountDTOList = new ArrayList<>();
    List<AccountInsertDTO> accountInsertDTOList = agregarCuentas();

    accountInsertDTOList.forEach(accountInsertDTO -> {
      UserDTO userExist = userService.getUser(accountInsertDTO.getUsuario());
      if (userExist != null) {
        BankAccountDTO bankAccountExist = getUserName(accountInsertDTO.getUsuario());
        if (bankAccountExist == null) {
          BankAccountDTO bankAccount = buildBankAccount(accountInsertDTO.getUsuario(), accountInsertDTO.isActivo(), accountInsertDTO.getCountry(), accountInsertDTO.getDateTime());
          accountDTOList.add(bankAccount);
          mongoTemplate.save(bankAccount);
        } else {
          LOGGER.info("Bank existe");
          accountDTOList.add(buildBankAccount(accountInsertDTO.getUsuario(), accountInsertDTO.isActivo(), accountInsertDTO.getCountry(), accountInsertDTO.getDateTime()));
        }
      } else {
        LOGGER.info("No existe el usuario registrado");
      }
    });

    //Imprime en la Consola cuales son los records encontrados en la coleccion
    //bankAccountCollection de la mongo db
    mongoTemplate.findAll(BankAccountDTO.class).stream().map(bankAccountDTO -> bankAccountDTO.getUserName()).forEach(
        (user) -> LOGGER.info("User stored in bankAccountCollection " + user)
    );
    return accountDTOList;
  }

  private List<AccountInsertDTO> agregarCuentas() {
    List<AccountInsertDTO> accountInsertDTOList = new ArrayList<>();
    AccountInsertDTO accountInsertDTO = new AccountInsertDTO("alex", Country.MX, LocalDateTime.now().minusDays(7), true);
    accountInsertDTOList.add(accountInsertDTO);
    accountInsertDTO = new AccountInsertDTO("user1@wizeline.com", Country.FR, LocalDateTime.now().minusDays(7), false);
    accountInsertDTOList.add(accountInsertDTO);
    accountInsertDTO = new AccountInsertDTO("user2@wizeline.com", Country.US, LocalDateTime.now().minusMonths(2), false);
    accountInsertDTOList.add(accountInsertDTO);
    accountInsertDTO = new AccountInsertDTO("user3@wizeline.com", Country.MX, LocalDateTime.now().minusYears(4), true);
    accountInsertDTOList.add(accountInsertDTO);
    return accountInsertDTOList;
  }

  @Override
  public void deleteAccounts() {
    bankAccountRepository.deleteAll();
  }

  @Override
  public List<BankAccountDTO> getAccountByUser(String user) {
    Query query = new Query();
    query.addCriteria(Criteria.where("userName").is(user));
    return mongoTemplate.find(query, BankAccountDTO.class);
  }

  @Override
  public BankAccountDTO getAccountDetails(String user, String lastUsage) {
    DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    LocalDate usage = LocalDate.parse(lastUsage, dateformatter);
    return buildBankAccount(user, true, Country.MX, usage.atStartOfDay());
  }

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
  public List<BankAccountDTO> getEncryptedAccount() {
    List<BankAccountDTO> accounts = getAccounts();
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
      for (BankAccountDTO account : accounts) {
        String accountName = account.getAccountName();
        byte[] arrAccountName = accountName.getBytes();
        byte[] accountNameCipher = new byte[cipher.getOutputSize(arrAccountName.length)];
        int ctAccountNameLength = cipher.update(arrAccountName, 0, arrAccountName.length, accountNameCipher, 0);
        ctAccountNameLength += cipher.doFinal(accountNameCipher, ctAccountNameLength);
        account.setAccountName(accountNameCipher.toString());

        String accountCountry = account.getCountry();
        byte[] arrAccountCountry = accountCountry.getBytes();
        byte[] accountCountryCipher = new byte[cipher.getOutputSize(arrAccountCountry.length)];
        int ctAccountCountryLength = cipher.update(arrAccountCountry, 0, arrAccountCountry.length, accountCountryCipher, 0);
        ctAccountCountryLength += cipher.doFinal(accountCountryCipher, ctAccountCountryLength);
        account.setCountry(accountCountryCipher.toString());

      }
    } catch (InvalidAlgorithmParameterException e) {
      throw new RuntimeException(e);
    } catch (NoSuchPaddingException e) {
      throw new RuntimeException(e);
    } catch (ShortBufferException e) {
      throw new RuntimeException(e);
    } catch (IllegalBlockSizeException e) {
      throw new RuntimeException(e);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    } catch (BadPaddingException e) {
      throw new RuntimeException(e);
    } catch (NoSuchProviderException e) {
      throw new RuntimeException(e);
    } catch (InvalidKeyException e) {
      throw new RuntimeException(e);
    }
    return accounts;
  }

  public BankAccountDTO getUserName(String username) {
    Query query = new Query();
    query.addCriteria(Criteria.where("userName").is(username));
    return mongoTemplate.findOne(query, BankAccountDTO.class);
  }
}
