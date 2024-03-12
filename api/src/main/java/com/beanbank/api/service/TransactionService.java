package com.beanbank.api.service;

import com.beanbank.api.model.Transaction;
import com.beanbank.api.repository.TransactionRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<Map<String, Object>> getHistory(int senderID, int receiverID) {
        List<Object[]> transactionDetails = transactionRepository.transactionDetails(senderID, receiverID);
        List<Map<String, Object>> formattedDetails = new ArrayList<>();

        for (Object[] row : transactionDetails) {
            Map<String, Object> rowData = new HashMap<>();
            rowData.put("transactionID", row[0]);
            rowData.put("senderID", row[1]);
            rowData.put("senderName", row[2]);
            rowData.put("receiverID", row[3]);
            rowData.put("receiverName", row[4]);
            rowData.put("transactionTypeName", row[5]);
            rowData.put("transactionAmount", row[6]);
            rowData.put("transactionTimeStamp", row[7]);
            formattedDetails.add(rowData);
        }

        return formattedDetails;
    }
}
