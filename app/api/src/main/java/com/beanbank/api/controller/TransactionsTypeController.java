package com.beanbank.api.controller;

import com.beanbank.api.service.TransactionTypeService;
import com.models.TransactionsType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionsTypeController {
    final
    TransactionTypeService transactionsTypeService;

    public TransactionsTypeController(TransactionTypeService transactionsTypeService) {
        this.transactionsTypeService = transactionsTypeService;
    }

    @RequestMapping(
            value = "/transactionsType/{transactionsTypeID}",
            method = RequestMethod.GET
    )
    public TransactionsType getTransactionTypeByID(@PathVariable(value = "transactionsTypeID") int transactionsTypeID) {
        return transactionsTypeService.getTransactionTypeByID(transactionsTypeID);
    }

    @RequestMapping(
            value = "/transactionsType",
            method = RequestMethod.GET
    )
    public List<TransactionsType> getTransactionTypes(){
        return transactionsTypeService.getTransactionTypes();
    }
}
