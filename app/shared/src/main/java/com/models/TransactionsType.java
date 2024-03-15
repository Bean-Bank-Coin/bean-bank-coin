package com.models;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TransactionsType")
@NoArgsConstructor
public class TransactionsType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TransactionTypeID")
    private int transactionTypeID;

    @Column(name = "TransactionTypeName")
    private String transactionTypeName;

    public TransactionsType(
        int transactionTypeID,
        String transactionTypeName   
    ) {
        this.transactionTypeID = transactionTypeID;
        this.transactionTypeName = transactionTypeName;
    }

    public int getTransactionTypeID() {
        return transactionTypeID;
    }

    public void setTransactionTypeID(int transactionTypeID) {
        this.transactionTypeID = transactionTypeID;
    }

    public String getTransactionTypeName() {
        return transactionTypeName;
    }

    public void setTransactionTypeName(String transactionTypeName) {
        this.transactionTypeName = transactionTypeName;
    }
}
