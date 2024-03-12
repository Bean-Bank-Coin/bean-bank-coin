package com.beanbank.api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.models.BeanType;
import com.beanbank.api.repository.BeanTypeRepository;

@Service
public class BeanTypeService {
    final BeanTypeRepository beanTypeRepository;

    public BeanTypeService(BeanTypeRepository beanTypeRepository) {
        this.beanTypeRepository = beanTypeRepository;
    }

    public BeanType addBeanType(BeanType beanType) {
        return beanTypeRepository.save(beanType);
    }

    public List<BeanType> getBeanTypes() {
        return beanTypeRepository.findAll();
    }
}
