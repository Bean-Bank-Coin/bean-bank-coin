package com.beanbank.api.serviceTests;

import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.beanbank.api.repository.BeanTypeRepository;
import com.beanbank.api.service.BeanTypeService;

@ExtendWith(MockitoExtension.class)
public class BeanTypeServiceTests {
    @Mock
    private BeanTypeRepository beanTypeRepository;
    private BeanTypeService underTest;

    @BeforeEach
    void setUp() {
        underTest = new BeanTypeService(beanTypeRepository);
    }

    @Test
    public void canGetBeanTypes() {
        // Given
        // When
        underTest.getBeanTypes();

        // Then
        verify(beanTypeRepository).findAll();
    }
}