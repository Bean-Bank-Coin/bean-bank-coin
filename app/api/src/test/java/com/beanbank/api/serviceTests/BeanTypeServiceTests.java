package com.beanbank.api.serviceTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.beanbank.api.repository.BeanTypeRepository;
import com.beanbank.api.service.BeanTypeService;
import com.models.BeanType;

@ExtendWith(MockitoExtension.class)
public class BeanTypeServiceTests {
    @Mock
    private BeanTypeRepository beanTypeRepository;
    private BeanTypeService underTest;

    @BeforeEach
    void setUp(){
        underTest = new BeanTypeService(beanTypeRepository);
    }

    @Test
    public void canAddBeanType() {
        //Given
        BeanType beanType = new BeanType(
            1,
            "bean name",
            "BEN",
            new BigDecimal(15.00)
        );

        //When
        underTest.addBeanType(beanType);

        //Then
        ArgumentCaptor<BeanType> beanTypeArgumentCaptor = ArgumentCaptor.forClass(BeanType.class);
        verify(beanTypeRepository).save(beanTypeArgumentCaptor.capture());

        BeanType capturedBeanType = beanTypeArgumentCaptor.getValue();
        assertThat(capturedBeanType).isEqualTo(beanType);
    }

    @Test
    public void canGetBeanTypes() {
        //Given
        //When
        underTest.getBeanTypes();

        //Then
        verify(beanTypeRepository).findAll();
    }
}