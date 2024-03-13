package com.beanbank.api.service;

import com.beanbank.api.repository.AccountRepository;
import com.models.Account;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public List<Account> getAccountsForUser(int userID) {
        return accountRepository.findByUserID(userID);
    }

    public Account updateBalance(int accountID, Account newAccount) {
        Account account = accountRepository.findById(accountID).get();
        account.setBalanceAmount(newAccount.getBalanceAmount());
        return accountRepository.save(account);
    }
}