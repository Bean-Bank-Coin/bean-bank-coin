package com.beanbank.api.repository;

import com.models.TransactionsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsTypeRepository extends JpaRepository<TransactionsType, Integer> {

}
