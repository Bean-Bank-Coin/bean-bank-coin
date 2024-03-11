package com.beanbank.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beanbank.api.model.Transaction;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findBySenderIDOrReceiverID(int senderID, int receiverID);
}
