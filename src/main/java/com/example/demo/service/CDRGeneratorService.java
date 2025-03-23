package com.example.demo.service;

import com.example.demo.model.CDR;
import com.example.demo.model.Subscriber;
import com.example.demo.dao.CDRRepository;
import com.example.demo.dao.SubscriberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Сервис для генерации CDR-записей.
 * Генерирует случайные CDR-записи для всех абонентов за год.
 */
@Service
public class CDRGeneratorService {

    @Autowired
    private CDRRepository cdrRepository;

    @Autowired
    private SubscriberRepository subscriberRepository;

    /**
     * Генерирует CDR-записи для всех абонентов за год.
     * Записи создаются в хронологическом порядке.
     */
    public void generateCDRsForYear() {
        List<Subscriber> subscribers = subscriberRepository.findAll();
        Random random = new Random();
        LocalDateTime startDate = LocalDateTime.now().minusYears(1);

        for (int i = 0; i < 365; i++) {
            LocalDateTime currentDate = startDate.plusDays(i);
            for (Subscriber subscriber : subscribers) {
                int callCount = random.nextInt(10); // Случайное количество звонков
                for (int j = 0; j < callCount; j++) {
                    CDR cdr = new CDR();
                    cdr.setCallType(random.nextBoolean() ? "01" : "02"); // Случайный тип звонка
                    cdr.setCaller(subscriber); // Устанавливаем caller как объект Subscriber
                    cdr.setReceiver(subscribers.get(random.nextInt(subscribers.size()))); // Устанавливаем receiver как объект Subscriber
                    cdr.setStartTime(currentDate.plusMinutes(random.nextInt(1440))); // Случайное время начала
                    cdr.setEndTime(cdr.getStartTime().plusMinutes(random.nextInt(60))); // Случайная длительность
                    cdrRepository.save(cdr);
                }
            }
        }

    }

    /**
     * Генерирует 10 абонентов с случайными номерами.
     */
    public List<Subscriber> generateSubscribers() {
        List<Subscriber> subscribers = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            Subscriber subscriber = new Subscriber();
            subscriber.setMsisdn("79" + String.format("%09d", random.nextInt(1_000_000_000))); // Генерация случайного номера
            subscribers.add(subscriber);
        }

        // Сохраняем абонентов в базу данных
        return subscriberRepository.saveAll(subscribers);
    }
}