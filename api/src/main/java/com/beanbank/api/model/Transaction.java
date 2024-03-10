package com.beanbank.api.model;

import jakarta.persistence.*;

import java.util.Date;

import io.micrometer.common.lang.NonNull;

@Entity
@Table(name = "Transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TransactionID")
    private int transactionID;

    @Column(name = "SenderID")
    private int senderID;

    @Column(name = "ReceiverID")
    private int receiverID;

    @Column(name = "TransactionTypeID")
    private int transactionTypeID;

    @Column(name = "TransactionAmount")
    private int transactionAmount;

    @Column(name = "TransactionTimestamp")
    private Date transactionTimestamp;

    public int getTransactionID() {
        return this.transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getSenderID() {
        return this.senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public int getReceiverID() {
        return this.receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public int getTransactionTypeID() {
        return this.transactionTypeID;
    }

    public void setTransactionTypeID(int transactionTypeID) {
        this.transactionTypeID = transactionTypeID;
    }

    public int getTransactionAmount() {
        return this.transactionAmount;
    }

    public void setTransactionAmount(int transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Date getTransactionTimestamp() {
        return this.transactionTimestamp;
    }

    public void setTransactionTimestamp(Date transactionTimestamp) {
        this.transactionTimestamp = transactionTimestamp;
    }
}
