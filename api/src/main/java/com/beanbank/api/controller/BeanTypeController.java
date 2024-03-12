package com.beanbank.api.controller;

import com.beanbank.api.model.BeanType;
import com.beanbank.api.service.BeanTypeService;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.*;

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
