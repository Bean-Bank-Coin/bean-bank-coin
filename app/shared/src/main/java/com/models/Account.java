package com.models;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Account")
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountID")
    private int accountID;

    @Column(name = "UserID")
    private int userID;

    @Column(name = "BeanTypeID")
    private int beanTypeID;

    @Column(name = "BalanceAmount")
    private BigDecimal balanceAmount;

    @Column(name = "DateCreated")
    private Date dateCreated;

    @Column(name = "IsClosed")
    private Boolean isClosed;

    public Account(
            int accountID,
            int userID,
            int beanTypeID,
            BigDecimal balanceAmount,
            Boolean isClosed) {
        this.accountID = accountID;
        this.userID = userID;
        this.beanTypeID = beanTypeID;
        this.balanceAmount = balanceAmount;
        this.isClosed = isClosed;
    }

    @Override
    public boolean equals(Object otherAccounObject) {
        if (this == otherAccounObject) return true;
        if (otherAccounObject == null || getClass() != otherAccounObject.getClass()) return false;
        Account account = (Account) otherAccounObject;
        return  (accountID == account.accountID &&
                userID == account.userID &&
                beanTypeID == account.beanTypeID &&
                isClosed == account.isClosed &&
                Objects.equals(balanceAmount, account.balanceAmount));
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountID, userID, beanTypeID, balanceAmount, isClosed);
    }

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
