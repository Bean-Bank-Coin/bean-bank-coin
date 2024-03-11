package com.beanbank.api.controller;

import com.beanbank.api.model.Transaction;
import com.beanbank.api.service.TransactionService;

import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionController {
    final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RequestMapping(value = "/transaction", method = RequestMethod.PUT)
    public Transaction addTransaction(@RequestBody Transaction transaction) {
        return transactionService.createTransaction(transaction);
    }

    @RequestMapping(value = "/transaction/{accountID}", method = RequestMethod.GET)
    public List<Transaction> getTransactions(@PathVariable(name = "accountID") int accountID) {
        return transactionService.getTransactionsForAccount(accountID);
    }
}
