package com.beanbank.api.model;

import jakarta.persistence.*;


@Entity
@Table(name = "BeanType")
public class BeanType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BeanTypeID")
    private int beanTypeID;

    @Column(name = "BeanName")
    private String beanName;

    @Column(name = "BeanSymbol")
    private String beanSymbol;

    @Column(name = "ValueInRands")
    private int valueInRands;

    public int getBeanTypeID() {
        return beanTypeID;
    }

    public void setBeanTypeID(int beanTypeID) {
        this.beanTypeID = beanTypeID;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanSymbol() {
        return beanSymbol;
    }

    public void setBeanSymbol(String beanSymbol) {
        this.beanSymbol = beanSymbol;
    }

    public int getValueInRands() {
        return valueInRands;
    }

    public void setValueInRands(int valueInRands) {
        this.valueInRands = valueInRands;
    }
}
