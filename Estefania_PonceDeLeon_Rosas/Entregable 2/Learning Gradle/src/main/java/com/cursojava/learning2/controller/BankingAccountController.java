package com.cursojava.learning2.controller;

import com.cursojava.learning2.model.BankAccountDTO;
import com.cursojava.learning2.model.ResponseDTO;
import com.cursojava.learning2.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/account")
public class BankingAccountController {

    @Autowired
    private BankAccountService bankAccountService;
    @GetMapping(value = "getUserAccount", produces = "application/json")
    public BankAccountDTO getUserAccount(@RequestParam String user, String date){
        return this.bankAccountService.getAccountDetails(user,date);
    }
    @GetMapping(value = "getAccounts", produces = "application/json")
    public List<BankAccountDTO> getAccounts(){
        return this.bankAccountService.getAccounts();
    }

    @GetMapping(value = "getAccountsGroupByType", produces = "application/json")
    public Map<String, List<BankAccountDTO>> getAccountsGroupByType(){
        List<BankAccountDTO> accounts = this.bankAccountService.getAccounts();
        Map<String, List<BankAccountDTO>> groupedAccounts;
        Function<BankAccountDTO, String> groupFunction = (account) -> account.getAccountType().toString();
        groupedAccounts = accounts.stream().collect(Collectors.groupingBy(groupFunction));
        return groupedAccounts;
    }
}