package com.beanbank.api.controller;

import com.beanbank.api.model.TransactionsType;
import com.beanbank.api.service.TransactionTypeService;
import org.springframework.web.bind.annotation.*;

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
