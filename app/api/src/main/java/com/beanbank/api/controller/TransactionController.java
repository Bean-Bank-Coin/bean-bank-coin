package com.beanbank.api.controller;

import com.beanbank.api.service.TransactionService;
import com.beanbank.api.service.UserService;
import com.models.Transaction;
import com.models.User;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TransactionController {
    final TransactionService transactionService;
    final UserService userService;

    public TransactionController(TransactionService transactionService, UserService userService) {
        this.transactionService = transactionService;
        this.userService = userService;
    }

    @RequestMapping(value = "/transaction/{userID}", method = RequestMethod.PUT)
    public Transaction addTransaction(@PathVariable("userID") int userID, @RequestBody Transaction transaction) {
        User foundUser = userService.findUser(userID);

        if (!UserController.currentUser.equals(foundUser.getUsername())) {
            return null;
        }

        return transactionService.makeTransaction(transaction);
    }

    @RequestMapping(value = "/transaction/history/{accountID}", method = RequestMethod.GET)
    public List<Map<String, Object>> getHistory(
            @PathVariable(name = "accountID") int accountID) {
        return transactionService.getHistory(accountID);
    }
}
