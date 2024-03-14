package com.beanbank.api.serviceTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.beanbank.api.repository.AccountRepository;
import com.beanbank.api.service.AccountService;
import com.models.Account;

@ExtendWith(MockitoExtension.class)
class AccountServiceTests{

    @Mock
    private AccountRepository accountRepository;
    private AccountService underTest;

    @BeforeEach
    void setUp(){
        underTest = new AccountService(accountRepository);
    }

    @Test
    public void canCreateAnAccount() {
        //Given
        Account account = new Account(
            1, 
            1, 
            1, 
            new BigDecimal(1500), 
            new Date(), 
            false
        );

        //When
        underTest.createAccount(account);

        //Then
        ArgumentCaptor<Account> accounArgumentCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository).save(accounArgumentCaptor.capture());

        Account capturedAccount = accounArgumentCaptor.getValue();
        assertThat(capturedAccount).isEqualTo(account);
    }

    @Test
    public void canGetAccountsForUser() {
        //Given
        int userID = 1;

        //When
        underTest.getAccountsForUser(userID);

        //Then
        verify(accountRepository).findByUserID(userID);
    }

    @Test
    public void updateBalance() {
        //Given
        int accountID = 1;
        BigDecimal newBalance = BigDecimal.valueOf(2000);
        Account account = new Account(
            accountID, 
            1, 
            1, 
            new BigDecimal(1500), 
            new Date(), 
            false
        );

        Account updatedAccount = new Account(
            accountID, 
            1, 
            1, 
            newBalance, 
            new Date(), 
            false
        );

        when(accountRepository.findById(accountID)).thenReturn(Optional.of(account));
        
        //When
        underTest.updateBalance(accountID, updatedAccount);

        //Then
        ArgumentCaptor<Account> accounArgumentCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository).save(accounArgumentCaptor.capture());

        Account capturedAccount = accounArgumentCaptor.getValue();
        assertThat(capturedAccount).isEqualTo(updatedAccount);
    }
}