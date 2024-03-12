package com.beanbank.api.controller;

import com.beanbank.api.service.TransactionService;
import com.models.Transaction;
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
        return transactionService.makeTransaction(transaction);
    }

    @RequestMapping(value = "/transaction/history/{accountID}", method = RequestMethod.GET)
    public List<Map<String, Object>> getHistory(
            @PathVariable(name = "accountID") int accountID) {
        return transactionService.getHistory(accountID);
    }
}
