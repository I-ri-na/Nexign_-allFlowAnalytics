package com.example.demo.controler;

import com.example.demo.model.CDR;
import com.example.demo.model.Subscriber;
import com.example.demo.dao.CDRRepository;
import com.example.demo.dao.SubscriberRepository;
import com.example.demo.dto.UDR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Контроллер для работы с UDR-отчетами.
 * Предоставляет REST API для получения UDR-отчетов по абонентам.
 */
@RestController
@RequestMapping("/udr")
public class UDRController {

    @Autowired
    private CDRRepository cdrRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    /**
     * Возвращает UDR-отчет для указанного абонента.
     *
     * @param msisdn Номер абонента.
     * @param month  Месяц, за который требуется отчет (опционально).
     * @return UDR-отчет в формате JSON.
     */
    @GetMapping("/{msisdn}")
    public UDR getUDR(@PathVariable String msisdn, @RequestParam(required = false) String month) {
        Optional<Subscriber> subscriberOpt = subscriberRepository.findByMsisdn(msisdn);
        if (subscriberOpt.isEmpty()) {
            throw new RuntimeException("Subscriber not found");
        }

        Subscriber subscriber = subscriberOpt.get();
        LocalDateTime start = month != null ? LocalDateTime.parse(month + "-01T00:00:00") : LocalDateTime.now().minusYears(1);
        LocalDateTime end = month != null ? start.plusMonths(1) : LocalDateTime.now();

        List<CDR> cdrs = cdrRepository.findByCallerAndStartTimeBetween(subscriber, start, end);
        return new UDR(msisdn, cdrs);
    }
}