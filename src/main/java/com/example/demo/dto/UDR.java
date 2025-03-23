package com.example.demo.dto;

import com.example.demo.model.CDR;
import lombok.Data;
import java.time.Duration;
import java.util.List;

/**
 * DTO (Data Transfer Object) для UDR-отчета.
 * Содержит информацию о длительности входящих и исходящих звонков абонента.
 */
@Data
public class UDR {
    private String msisdn;
    private CallDuration incomingCall;
    private CallDuration outcomingCall;

    /**
     * Конструктор для создания UDR-отчета.
     *
     * @param msisdn Номер абонента.
     * @param cdrs   Список CDR-записей для расчета длительности звонков.
     */
    public UDR(String msisdn, List<CDR> cdrs) {
        this.msisdn = msisdn;
        this.incomingCall = new CallDuration();
        this.outcomingCall = new CallDuration();

        for (CDR cdr : cdrs) {
            Duration duration = Duration.between(cdr.getStartTime(), cdr.getEndTime());
            if ("02".equals(cdr.getCallType())) {
                incomingCall.addDuration(duration);
            } else if ("01".equals(cdr.getCallType())) {
                outcomingCall.addDuration(duration);
            }
        }
    }

    /**
     * Внутренний класс для хранения длительности звонков.
     */
    @Data
    public static class CallDuration {
        private long totalSeconds;

        /**
         * Добавляет длительность звонка.
         *
         * @param duration Длительность звонка.
         */
        public void addDuration(Duration duration) {
            this.totalSeconds += duration.getSeconds();
        }

        /**
         * Возвращает общую длительность звонков в формате "HH:mm:ss".
         *
         * @return Форматированная строка с длительностью.
         */
        public String getTotalTime() {
            long hours = totalSeconds / 3600;
            long minutes = (totalSeconds % 3600) / 60;
            long seconds = totalSeconds % 60;
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        }
    }
}