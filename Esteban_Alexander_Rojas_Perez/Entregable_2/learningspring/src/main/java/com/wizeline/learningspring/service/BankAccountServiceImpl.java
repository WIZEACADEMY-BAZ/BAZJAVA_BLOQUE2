package com.wizeline.learningspring.service;

import java.security.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import com.wizeline.learningspring.LearningspringApplication;
import com.wizeline.learningspring.utils.Utils;
import com.wizeline.learningspring.utils.exceptions.ExcepcionGenerica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.wizeline.learningspring.enums.Country;
import com.wizeline.learningspring.model.BankAccountDTO;
import com.wizeline.learningspring.repository.BankingAccountRepository;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

@Service
public class BankAccountServiceImpl implements BankAccountService {
    private static final Logger LOGGER = Logger.getLogger(LearningspringApplication.class.getName());

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

        mongoTemplate.findAll(BankAccountDTO.class).stream().map(bankAccountDTO -> bankAccountDTO.getUserName()).forEach(
                (user) -> {
                        LOGGER.info("Usuario guardado: " + user );
                });

        return accountDTOList;
    }

    @Override
    public void deleteAccounts(Long accountNumber) {
        Optional<BankAccountDTO> result = bankAccountRepository.findByAccountNumber(accountNumber);

        if (result.isPresent()){
            bankAccountRepository.deleteById(accountNumber);
        }else {
            throw new ExcepcionGenerica("Fallo al eliminar");
        }
    }

    @Override
    public void updateAccount(Long accountNumber, BankAccountDTO request) {
        Optional<BankAccountDTO> result = Optional.ofNullable(getUserByAccountNumber(accountNumber));

        if (result.isPresent()){
            BankAccountDTO requestDTO = result.get();
            requestDTO.setAccountName(request.getAccountName());
            requestDTO.setUserName(request.getUserName());
            requestDTO.setAccountBalance(request.getAccountBalance());
            requestDTO.setAccountType(request.getAccountType());
            requestDTO.setCountry(request.getCountry());
            requestDTO.setAccountActive(request.isAccountActive());
            request.setLastUsage(request.getLastUsage());

            bankAccountRepository.save(requestDTO);
        }else {
            throw new ExcepcionGenerica("Fallo al actualizar");
        }
    }

    @Override
    public List<BankAccountDTO> getAccountByUser(String user) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userName").is(user));
        return mongoTemplate.find(query, BankAccountDTO.class);
    }

    @Override
    public List<BankAccountDTO> encryptedAccounts() {
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

            for (int i = 0; i < accounts.size(); i++) {
                String accountName = accounts.get(i).getAccountName();
                byte[] arrAccountName = accountName.getBytes();
                byte [] accountNameCipher = new byte[cipher.getOutputSize(arrAccountName.length)];
                int ctAccountNameLength = cipher.update(arrAccountName, 0, arrAccountName.length, accountNameCipher, 0);
                ctAccountNameLength += cipher.doFinal(accountNameCipher, ctAccountNameLength);
                accounts.get(i).setAccountName(accountNameCipher.toString());

                String accountCountry = accounts.get(i).getCountry();
                byte[] arrAccountCountry = accountCountry.getBytes();
                byte[] accountCountryCipher = new byte[cipher.getOutputSize(arrAccountCountry.length)];
                int ctAccountCountryLength = cipher.update(arrAccountCountry, 0, arrAccountCountry.length, accountCountryCipher, 0);
                ctAccountNameLength += cipher.doFinal(accountCountryCipher, ctAccountCountryLength);
                accounts.get(i).setCountry(accountCountryCipher.toString());

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

        return accounts;
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
        bankAccountDTO.setAccountName("Cuenta ficticia ".concat(Utils.randomInt()));
        bankAccountDTO.setUserName(user);
        bankAccountDTO.setAccountBalance(Utils.randomBalance());
        bankAccountDTO.setAccountType(Utils.pickRandomAccountType());
        bankAccountDTO.setCountry(Utils.getCountry(country));
        bankAccountDTO.getLastUsage();
        bankAccountDTO.setCreationDate(lastUsage);
        bankAccountDTO.setAccountActive(isActive);
        return bankAccountDTO;
    }

    public BankAccountDTO getUserByAccountNumber(Long accountNumber){
        return bankAccountRepository.findByAccountNumber(accountNumber).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("No encontrado")));
    }

}
