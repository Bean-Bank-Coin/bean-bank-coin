package com.beanbank.api.service;

import com.beanbank.api.model.Account;
import com.beanbank.api.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    final
    AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Create Account
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    // READ
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    //Update Balance
    public Account updateBalance(int accountID, Account newAccount){
        Account account = accountRepository.findById((long) accountID).get();
        account.setBalanceAmount(newAccount.getBalanceAmount());
        return accountRepository.save(account);
    }
}