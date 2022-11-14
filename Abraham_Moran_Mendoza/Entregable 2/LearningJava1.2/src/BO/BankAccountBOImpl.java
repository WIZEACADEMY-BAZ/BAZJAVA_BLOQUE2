package BO;

import DTO.BankAccountDTO;
import enums.Country;
import utils.Utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BankAccountBOImpl implements BankAccountBO{
  @Override
  public List<BankAccountDTO> getAccounts() {
    List<BankAccountDTO> accountDTOList = new ArrayList<>();
    accountDTOList.add(buildBankAccount("user3@wizeline.com", true, Country.MX, LocalDateTime.now().minusDays(7)));
    accountDTOList.add(buildBankAccount("user1@wizeline.com", false, Country.FR, LocalDateTime.now().minusMonths(2)));
    accountDTOList.add(buildBankAccount("user2@wizeline.com" ,false, Country.US, LocalDateTime.now().minusYears(4)));
    return accountDTOList;
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
    bankAccountDTO.setUser(user);
    bankAccountDTO.setAccountBalance(Utils.randomBalance());
    bankAccountDTO.setAccountType(Utils.pickRandomAccountType());
    bankAccountDTO.setCountry(Utils.getCountry(country));
    bankAccountDTO.getLastUsage();
    bankAccountDTO.setCreationDate(lastUsage);
    bankAccountDTO.setAccountActive(isActive);
    return bankAccountDTO;
  }
}
