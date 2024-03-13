package com.models;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "BeanType")
@NoArgsConstructor
public class BeanType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BeanTypeID")
    private int beanTypeID;

    @Column(name = "BeanName", unique = true)
    private String beanName;

    @Column(name = "BeanSymbol", unique = true)
    private String beanSymbol;

    @Column(name = "ValueInRands")
    private BigDecimal valueInRands;

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

    public BigDecimal getValueInRands() {
        return valueInRands;
    }

    public void setValueInRands(BigDecimal valueInRands) {
        this.valueInRands = valueInRands;
    }
}
