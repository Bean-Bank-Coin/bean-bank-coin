package com.beanbank.api.serviceTests;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.beanbank.api.repository.TransactionsTypeRepository;
import com.beanbank.api.service.TransactionTypeService;
import com.models.TransactionsType;

@ExtendWith(MockitoExtension.class)
public class TransactionsTypeServiceTests {
    @Mock
    private TransactionsTypeRepository transactionsTypeRepository;
    private TransactionTypeService underTest;

    @BeforeEach
    void setUp(){
        underTest = new TransactionTypeService(transactionsTypeRepository);
    }

    @Test
    public void canGetTransactionTypeByID(){
        //Given
        int transactionTypeID = 1;
        TransactionsType transactionsType = new TransactionsType(
            transactionTypeID,
            "new transaction" 
        );
        when(transactionsTypeRepository.findById(transactionTypeID)).thenReturn(Optional.of(transactionsType));

        //When
        underTest.getTransactionTypeByID(transactionTypeID);

        //Then
        verify(transactionsTypeRepository).findById(transactionTypeID);
    }

    @Test
    public void canGetTransactionTypes(){
        //Given
        //When
        underTest.getTransactionTypes();

        //Then
        verify(transactionsTypeRepository).findAll();
    }
}
