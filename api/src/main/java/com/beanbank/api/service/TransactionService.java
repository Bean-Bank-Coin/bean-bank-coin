package com.beanbank.api.service;

import com.beanbank.api.model.Transaction;
import com.beanbank.api.repository.TransactionRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionsForAccount(int accountID) {
        return transactionRepository.findBySenderIDOrReceiverID(accountID, accountID);
    }
}
