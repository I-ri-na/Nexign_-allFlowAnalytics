package com.example.demo;


import com.example.demo.model.CDR;
import com.example.demo.model.Subscriber;
import com.example.demo.dao.CDRRepository;
import com.example.demo.service.CDRReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CDRReportServiceTest {

    @Mock
    private CDRRepository cdrRepository;

    @InjectMocks
    private CDRReportService cdrReportService;

    @Test
    void generateCDRReport() {
        // Подготовка данных
        Subscriber subscriber = new Subscriber();
        subscriber.setMsisdn("79097029932");

        CDR cdr = new CDR();
        cdr.setCallType("01");
        cdr.setCaller(subscriber);
        cdr.setReceiver(subscriber);
        cdr.setStartTime(LocalDateTime.now());
        cdr.setEndTime(LocalDateTime.now().plusMinutes(5));

        when(cdrRepository.findByCallerAndStartTimeAfterAndEndTimeBefore(any(), any(), any()))
                .thenReturn(Collections.singletonList(cdr));

        // Вызов метода
        String result = cdrReportService.generateCDRReport(subscriber, LocalDateTime.now().minusDays(1), LocalDateTime.now());

        // Проверка
        assertNotNull(result);
        assertTrue(result.contains("Report generated"));
    }
}