package com.example.demo.dao;

import com.example.demo.model.CDR;
import com.example.demo.model.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CDRRepository extends JpaRepository<CDR, Long> {
    List<CDR> findByCallerAndStartTimeBetween(Subscriber caller, LocalDateTime start, LocalDateTime end);
    List<CDR> findByCallerAndStartTimeAfterAndEndTimeBefore(Subscriber caller, LocalDateTime start, LocalDateTime end);
}