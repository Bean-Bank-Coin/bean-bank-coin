package com.beanbank.api.controller;

import com.beanbank.api.service.AccountService;
import com.models.Account;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {
    final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(value = "/accounts", method = RequestMethod.POST)
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @RequestMapping(value = "/accounts/{userID}", method = RequestMethod.GET)
    public List<Account> getAccounts(@PathVariable(value = "userID") int userID) {
        return accountService.getAccountsForUser(userID);
    }

    @RequestMapping(value = "/accounts/{accountID}", method = RequestMethod.PUT)
    public Account updateBalance(
            @PathVariable(value = "accountID") int accountID,
            @RequestBody Account accountDetails) {
        return accountService.updateBalance(accountID, accountDetails);
    }
}