package com.beanbank.api.service;

import com.beanbank.api.repository.*;
import com.models.Account;
import com.models.BeanType;
import com.models.Transaction;
import com.models.TransactionsType;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class TransactionService {
    final TransactionRepository transactionRepository;
    final UserRepository userRepository;
    final AccountRepository accountRepository;
    final TransactionsTypeRepository transactionsTypeRepository;
    final BeanTypeRepository beanTypeRepository;

    private Map<Integer, BigDecimal> beanConversionRates;

    public TransactionService(TransactionRepository transactionRepository,
            TransactionsTypeRepository transactionsTypeRepository,
            UserRepository userRepository,
            AccountRepository accountRepository, BeanTypeRepository beanTypeRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionsTypeRepository = transactionsTypeRepository;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.beanTypeRepository = beanTypeRepository;

        beanConversionRates = new HashMap<>();
        List<BeanType> beanTypes = beanTypeRepository.findAll();
        beanTypes.forEach(beanType -> beanConversionRates.put(beanType.getBeanTypeID(), beanType.getValueInRands()));
    }

    @Transactional
    public Transaction makeTransaction(Transaction transaction) {
        Optional<Account> optionalSenderAccount = accountRepository.findById(transaction.getSenderID());
        Optional<Account> optionalReceiverAccount = accountRepository.findById(transaction.getReceiverID());
        Optional<TransactionsType> optionalTransactionType = transactionsTypeRepository
                .findById(transaction.getTransactionTypeID());

        if (!(optionalSenderAccount.isPresent() && optionalReceiverAccount.isPresent())) {
            throw new RuntimeException("Both the sender and receiver must be valid");
        }

        if (!optionalTransactionType.isPresent()) {
            throw new RuntimeException("Transaction type is invalid");
        }

        Account senderAccount = optionalSenderAccount.get();
        Account receiverAccount = optionalReceiverAccount.get();
        TransactionsType transactionType = optionalTransactionType.get();
        String transactionTypeName = transactionType.getTransactionTypeName();

        if (transactionTypeName.equals("Deposit") || transactionTypeName.equals("Withdrawal")) {
            BigDecimal conversionRate = beanConversionRates.get(senderAccount.getBeanTypeID());
            BigDecimal senderBalance = senderAccount.getBalanceAmount();
            BigDecimal newBalance;

            if (transactionTypeName.equals("Deposit")) {
                newBalance = senderBalance.add(transaction.getTransactionAmount().divide(conversionRate));
            } else {
                newBalance = senderBalance.subtract(transaction.getTransactionAmount().divide(conversionRate));
            }

            senderAccount.setBalanceAmount(newBalance);
        } else if (transactionTypeName.equals("Transfer")) {
            BigDecimal senderConversionRate = beanConversionRates.get(senderAccount.getBeanTypeID());
            BigDecimal receiverConversionRate = beanConversionRates.get(receiverAccount.getBeanTypeID());

            BigDecimal senderBalance = senderAccount.getBalanceAmount();
            BigDecimal receiverBalance = receiverAccount.getBalanceAmount();

            BigDecimal senderNewBalance = senderBalance
                    .subtract(transaction.getTransactionAmount().divide(senderConversionRate));

            BigDecimal receiverNewBalance = receiverBalance
                    .add(transaction.getTransactionAmount().divide(receiverConversionRate));

            if (senderNewBalance.signum() == -1) {
                throw new RuntimeException("Sender cannot send more than available in account");
            }

            senderAccount.setBalanceAmount(senderNewBalance);
            receiverAccount.setBalanceAmount(receiverNewBalance);
        } else {
            throw new RuntimeException("Transaction type is invalid");
        }

        return transactionRepository.save(transaction);
    }

    public List<Map<String, Object>> getHistory(int accountID) {
        List<Object[]> transactionDetails = transactionRepository.transactionDetails(accountID);
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
