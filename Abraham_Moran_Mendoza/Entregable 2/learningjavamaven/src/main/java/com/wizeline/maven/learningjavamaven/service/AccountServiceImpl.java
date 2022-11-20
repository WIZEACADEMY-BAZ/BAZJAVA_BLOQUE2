package com.wizeline.maven.learningjavamaven.service;

import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;
import com.wizeline.maven.learningjavamaven.model.ResponseDTO;
import com.wizeline.maven.learningjavamaven.model.UserDTO;
import com.wizeline.maven.learningjavamaven.utils.Utils;
import com.wizeline.maven.learningjavamaven.utils.exceptions.ExcepcionGenerica;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static com.wizeline.maven.learningjavamaven.constants.Constants.*;
import static com.wizeline.maven.learningjavamaven.constants.MessageConstants.*;

@Service
public class AccountServiceImpl implements AccountService{

  private static final Logger LOGGER = Logger.getLogger(AccountServiceImpl.class.getName());

  @Autowired
  private UserService userService;
  @Autowired
  private BankAccountService bankAccountService;


  @Override
  public BankAccountDTO getAccount(String user, String password, String date){
    ResponseDTO response;

    UserDTO userDTO =  new UserDTO();
    userDTO.setUser(user);
    userDTO.setPassword(password);
    String lastUsage = date;

    if (Utils.isDateFormatValid(lastUsage)) {
      // Valida el password del usuario (password)
      if (Utils.isPasswordValid(userDTO.getPassword())) {
        response = userService.getUserMongo(userDTO.getUser(), userDTO.getPassword());
        if (response.getCode().equals(SUCCESS_CODE)) {

          BankAccountDTO bankAccountDTO = bankAccountService.getAccountDetails(userDTO.getUser(), lastUsage);
          return bankAccountDTO;
        }else{
          throw new ExcepcionGenerica("No se encontró el usuario");
        }
      } else {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password Incorrecto");
      }
    } else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato de Fecha Incorrecto");
    }
  }

  @Override
  public List<BankAccountDTO> getAccounts(){
    LOGGER.info(PROCESSING_GET_METHOD);
    List<BankAccountDTO> accounts = bankAccountService.getAccountsLocal();
    return accounts;
  }

  @Override
  public List<BankAccountDTO> getAccountByName(String name){
    List<BankAccountDTO> accounts = bankAccountService.getAccountsLocal();
    // Aquí implementaremos nuestro código de filtrar las cuentas por nombre utilizando optional
    Optional<String> Optionalnombre = Optional.of(name);
    String nombre = Optionalnombre.get();
    List<BankAccountDTO> accountsFiltered = bankAccountService.getAccountsLocal();
    accountsFiltered.clear();
    for (int i = 0; i < accounts.size(); i++) {
      if (accounts.get(i).getAccountName().indexOf(nombre) >= 0) {
        accountsFiltered.add(accounts.get(i));
        break;
      }
    }
    return accountsFiltered;
  }

  @Override
  public List<BankAccountDTO> getAccountByUser(String userString){
    String responseText = "";
    List<BankAccountDTO> accounts = bankAccountService.getAccountsLocal();
    List<BankAccountDTO> accountsFiltered = bankAccountService.getAccountsLocal();
    accountsFiltered.clear();
    Optional<Object> Optionaluser= Optional.of(userString);
    Object user = Optionaluser.get();
    for (int i = 0; i < accounts.size(); i++) {
      if (accounts.get(i).getUserName().indexOf(user.toString()) >= 0) {
        accountsFiltered.add(accounts.get(i));
      }
    }
    return accountsFiltered;
  }

  @Override
  public Map<String, List<BankAccountDTO>> getAccountsGroupByType(){
    String responseText = "";
    List<BankAccountDTO> accounts = bankAccountService.getAccountsLocal();

    // Aqui implementaremos la programación funcional
    Map<String, List<BankAccountDTO>> groupedAccounts;
    Function<BankAccountDTO, String> groupFunction = (account) -> account.getAccountType().toString();
    // Revisión: Uso de por lo menos 1 Stream de datos
    groupedAccounts = accounts.stream().collect(Collectors.groupingBy(groupFunction));

    return groupedAccounts;
  }

  @Override
  public List<BankAccountDTO> getEncryptedAccounts(){
    String responseText = "";
    List<BankAccountDTO> accounts = bankAccountService.getAccountsLocal();

    // Aquí implementaremos nuestro código de cifrar nuestras cuentas y regresarselas al usuario de manera cifrada
    byte[] keyBytes = new byte[]{
        0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef
    };
    byte[] ivBytes = new byte[]{
        0x00, 0x01, 0x02, 0x03, 0x00, 0x00, 0x00, 0x01
    };
    // Revisión: Uso de por lo menos 1 proveedor de cifrado
    Security.addProvider(new BouncyCastleProvider());

    // Revisión: Uso de por lo menos 1 llave de cifrado
    SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
    IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
    Cipher cipher = null;
    try {
      // Revisión: Uso de por lo menos 1 algoritmo de cifrado
      cipher = Cipher.getInstance("DES/CTR/NoPadding", "BC");
      cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
      // Cifraremos solamente el nombre y el country (pueden cifrar todos los parámetros que gusten)
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
}
