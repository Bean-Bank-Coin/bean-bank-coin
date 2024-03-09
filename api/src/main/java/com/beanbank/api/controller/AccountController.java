package com.beanbank.api.controller;

import java.util.List;

import com.beanbank.api.model.Account;
import com.beanbank.api.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    AccountService accountService;

    @RequestMapping(
            value="/accounts",
            method=RequestMethod.POST
    )
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @RequestMapping(
            value="/accounts",
            method=RequestMethod.GET
    )
    public List<Account> getAccounts() {
        return accountService.getAccounts();
    }

    @RequestMapping(
            value="/accounts/{accountID}",
            method=RequestMethod.PUT
    )
    public Account updateBalance(
            @PathVariable (value = "accountID") int accountID,
            @RequestBody Account accountDetails
    ){
        return accountService.updateBalance(accountID, accountDetails);
    }
}