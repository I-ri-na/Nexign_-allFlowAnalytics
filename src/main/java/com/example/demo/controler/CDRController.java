package com.example.demo.controler;

import com.example.demo.model.Subscriber;
import com.example.demo.dao.SubscriberRepository;
import com.example.demo.service.CDRGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для работы с CDR-записями.
 * Предоставляет REST API для генерации CDR-записей.
 */
@RestController
@RequestMapping("/cdr")
public class CDRController {

    @Autowired
    private CDRGeneratorService cdrGeneratorService;

    /**
     * Генерирует CDR-записи для всех абонентов за год.
     * Этот метод запускает процесс генерации CDR-записей, которые сохраняются в базу данных.
     *
     * @return Сообщение о завершении генерации CDR-записей.
     */
    @PostMapping("/generateCDR")
    public String generateCDRs() {
        cdrGeneratorService.generateCDRsForYear();
        return "CDR generation completed";
    }
    /**
     * Генерирует 10 абонентов и возвращает их список.
     */
    @PostMapping("/generateSubscribers")
    public List<Subscriber> generateSubscribers() {
        return cdrGeneratorService.generateSubscribers();
    }
}