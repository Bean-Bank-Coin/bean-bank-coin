package com.beanbank.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beanbank.api.model.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findBySenderIDOrReceiverID(int senderID, int receiverID);

    @Query("select " +
            "   t.transactionID, t.senderID, us.username," +
            "   t.receiverID, ur.username, tt.transactionTypeName," +
            "   t.transactionAmount, t.transactionTimestamp " +
            "from Transaction t" +
            "    inner join User us on t.senderID = us.userID" +
            "    inner join User ur on t.receiverID = ur.userID" +
            "    inner join TransactionsType tt on tt.transactionTypeID = t.transactionTypeID " +
            "where " +
            "   t.senderID = :senderID or " +
            "   t.receiverID = :recieverID")
    List<Object[]> transactionDetails(@Param("senderID") int senderID,
                                      @Param("recieverID") int recieverID);
}
