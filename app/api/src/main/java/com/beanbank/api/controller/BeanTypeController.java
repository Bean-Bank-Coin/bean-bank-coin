package com.beanbank.api.controller;

import com.beanbank.api.service.BeanTypeService;
import com.models.BeanType;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BeanTypeController {
    final BeanTypeService beanTypeService;

    public BeanTypeController(BeanTypeService beanTypeService) {
        this.beanTypeService = beanTypeService;
    }

    @Transactional
    @RequestMapping(value = "/beantype", method = RequestMethod.PUT)
    public BeanType addBeanType(@RequestBody BeanType beanType) {
        return beanTypeService.addBeanType(beanType);
    }

    @RequestMapping(value = "/beantype", method = RequestMethod.GET)
    public List<BeanType> getBeanTypes() {
        return beanTypeService.getBeanTypes();
    }
}
