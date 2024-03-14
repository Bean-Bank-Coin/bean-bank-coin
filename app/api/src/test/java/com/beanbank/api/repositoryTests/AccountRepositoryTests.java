package com.beanbank.api.repositoryTests;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.models.Account;
import com.beanbank.api.repository.AccountRepository;

@DataJpaTest
class AccountRepositoryTests{
    @Autowired
    AccountRepository underTest;

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }

    @Test
    void itShouldfindByUserID(){
        //Given
        int userID = 1;
        Account account = new Account(
            1, 
            userID, 
            1, 
            new BigDecimal(1500), 
            false
        );
        underTest.save(account);

        //When
        List<Account> foundAccounts = underTest.findByUserID(userID);
        List<Account> expectedAccounts = new ArrayList<Account>();
        expectedAccounts.add(account);


        //Then
        assertThat(foundAccounts).containsAnyElementsOf(expectedAccounts);
    }
}