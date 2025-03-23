package com.example.demo;
import com.example.demo.dao.CDRRepository;
import com.example.demo.model.CDR;
import com.example.demo.model.Subscriber;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CDRRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CDRRepository cdrRepository;

    @Test
    void findByCallerAndStartTimeAfterAndEndTimeBefore() {
        // Подготовка данных
        Subscriber subscriber = new Subscriber();
        subscriber.setMsisdn("79097029932");
        entityManager.persist(subscriber);

        CDR cdr = new CDR();
        cdr.setCallType("01");
        cdr.setCaller(subscriber);
        cdr.setReceiver(subscriber);
        cdr.setStartTime(LocalDateTime.now());
        cdr.setEndTime(LocalDateTime.now().plusMinutes(5));
        entityManager.persist(cdr);

        entityManager.flush();

        // Вызов метода
        var result = cdrRepository.findByCallerAndStartTimeAfterAndEndTimeBefore(
                subscriber,
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(1)
        );

        // Проверка
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(cdr.getCallType(), result.get(0).getCallType());
    }
}
