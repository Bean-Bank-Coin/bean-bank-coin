package com.beanbank.api.controller;

import com.beanbank.api.service.AccountService;
import com.beanbank.api.service.UserService;
import com.models.Account;
import com.models.User;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {
    final AccountService accountService;
    final UserService userService;

    public AccountController(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @RequestMapping(value = "/accounts/{username}", method = RequestMethod.POST)
    public Account createAccount(@PathVariable("username") String username, @RequestBody Account account) {
        if (!UserController.currentUser.equals(username)) {
            return null;
        }

        return accountService.createAccount(account);
    }

    @RequestMapping(value = "/accounts/{userID}", method = RequestMethod.GET)
    public List<Account> getAccounts(@PathVariable(value = "userID") int userID) {
        User foundUser = userService.findUser(userID);

        if (!UserController.currentUser.equals(foundUser.getUsername())) {
            return null;
        }

        return accountService.getAccountsForUser(userID);
    }
}