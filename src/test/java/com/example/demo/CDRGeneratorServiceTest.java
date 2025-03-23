package com.example.demo;

import com.example.demo.model.CDR;
import com.example.demo.model.Subscriber;
import com.example.demo.dao.CDRRepository;
import com.example.demo.dao.SubscriberRepository;
import com.example.demo.service.CDRGeneratorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CDRGeneratorServiceTest {

    @Mock
    private CDRRepository cdrRepository;

    @Mock
    private SubscriberRepository subscriberRepository;

    @InjectMocks
    private CDRGeneratorService cdrGeneratorService;

    @Test
    void generateCDRsForYear() {
        // Подготовка данных
        Subscriber subscriber = new Subscriber();
        subscriber.setMsisdn("79097029932");

        when(subscriberRepository.findAll()).thenReturn(Collections.singletonList(subscriber));

        // Вызов метода
        cdrGeneratorService.generateCDRsForYear();

        // Проверка
        verify(cdrRepository, atLeastOnce()).save(any(CDR.class));
    }
}