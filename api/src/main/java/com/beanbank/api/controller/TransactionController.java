package com.beanbank.api.controller;

import com.beanbank.api.model.Transaction;
import com.beanbank.api.service.TransactionService;

import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @RequestMapping(value = "/transaction/history/{senderID}/{receiverID}", method = RequestMethod.GET)
    public List<Map<String, Object>> getHistory(
            @PathVariable(name = "senderID") int senderID,
            @PathVariable(name = "receiverID") int receiverID
    ){
        return transactionService.getHistory(senderID, receiverID);
    }
}
