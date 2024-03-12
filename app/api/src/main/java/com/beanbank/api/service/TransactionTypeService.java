package com.beanbank.api.service;

import com.beanbank.api.repository.TransactionsTypeRepository;
import com.models.TransactionsType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionTypeService {
    final
    TransactionsTypeRepository transactionTypeRepository;

    public TransactionTypeService(TransactionsTypeRepository TransactionTypeRepository) {
        this.transactionTypeRepository = TransactionTypeRepository;
    }

    public TransactionsType getTransactionTypeByID(int transactionTypeID){
        return transactionTypeRepository.findById(transactionTypeID).get();
    }

    public List<TransactionsType> getTransactionTypes(){
        return transactionTypeRepository.findAll();
    }
}
