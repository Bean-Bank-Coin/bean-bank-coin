package com.beanbank.api.service;

import com.beanbank.api.repository.BeanTypeRepository;
import com.models.BeanType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeanTypeService {
    final BeanTypeRepository beanTypeRepository;

    public BeanTypeService(BeanTypeRepository beanTypeRepository) {
        this.beanTypeRepository = beanTypeRepository;
    }

    public List<BeanType> getBeanTypes() {
        return beanTypeRepository.findAll();
    }
}
