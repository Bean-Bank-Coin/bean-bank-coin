package com.beanbank.api.repository;

import com.beanbank.api.model.BeanType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeanTypeRepository extends JpaRepository<BeanType, Integer> {
}
