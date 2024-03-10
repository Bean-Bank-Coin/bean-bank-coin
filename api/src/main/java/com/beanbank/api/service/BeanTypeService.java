package com.beanbank.api.service;

import com.beanbank.api.model.BeanType;
import com.beanbank.api.repository.BeanTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
