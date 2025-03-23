package com.example.demo.dao;

import com.example.demo.model.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    Optional<Subscriber> findByMsisdn(String msisdn);
}
