package com.beanbank.api.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="AccountID")
    private int accountID;

    @Column(name="UserID")
    private int userID;

    @Column(name="BeanTypeID")
    private int beanTypeID;

    @Column(name="BalanceAmount")
    private BigDecimal balanceAmount;

    @Column(name="DateCreated")
    private Date dateCreated;

    @Column(name="IsClosed")
    private Boolean isClosed;

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getBeanTypeID() {
        return beanTypeID;
    }

    public void setBeanTypeID(int beanTypeID) {
        this.beanTypeID = beanTypeID;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Boolean getClosed() {
        return isClosed;
    }

    public void setClosed(Boolean closed) {
        isClosed = closed;
    }
}