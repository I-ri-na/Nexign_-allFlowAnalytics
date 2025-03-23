package com.example.demo.model;

import com.example.demo.model.Subscriber;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Сущность, представляющая CDR-запись (Call Data Record).
 * CDR-запись содержит информацию о звонке: тип звонка, номера абонентов,
 * время начала и окончания звонка.
 */
@Data
@Entity

public class CDR {
    /**
     * Уникальный идентификатор CDR-записи.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Тип звонка:
     * - "01" — исходящий звонок.
     * - "02" — входящий звонок.
     */
    private String callType;
    /**
     * Абонент, инициировавший звонок.
     */
    @ManyToOne
    @JoinColumn(name = "caller_id", nullable = false)
    private Subscriber caller;

    /**
     * Абонент, принимающий звонок.
     */
    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private Subscriber receiver; // Ссылка на абонента, принимающего звонок

    /**
     * Дата и время начала звонка.
     */
    private LocalDateTime startTime;

    /**
     * Дата и время окончания звонка.
     */
    private LocalDateTime endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public Subscriber getCaller() {
        return caller;
    }

    public void setCaller(Subscriber caller) {
        this.caller = caller;
    }

    public Subscriber getReceiver() {
        return receiver;
    }

    public void setReceiver(Subscriber receiver) {
        this.receiver = receiver;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}