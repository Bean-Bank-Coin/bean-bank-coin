package com.beanbank.api.repository;
import com.models.TransactionsType;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TransactionsTypeRepository extends JpaRepository<TransactionsType, Integer> {

}
