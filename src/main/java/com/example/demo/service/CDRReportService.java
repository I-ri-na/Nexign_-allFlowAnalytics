package com.example.demo.service;

import com.example.demo.model.CDR;
import com.example.demo.model.Subscriber;
import com.example.demo.dao.CDRRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.nio.file.Files;
import java.nio.file.Paths;
/**
 * Сервис для генерации CDR-отчетов.
 * Генерирует отчеты в формате CSV для указанного абонента за указанный период.
 */
@Service
public class CDRReportService {

    @Autowired
    private CDRRepository cdrRepository;

    /**
     * Генерирует CDR-отчет для указанного абонента за указанный период.
     *
     * @param subscriber Абонент, для которого генерируется отчет.
     * @param start      Начало периода.
     * @param end        Конец периода.
     * @return Сообщение о результате генерации отчета.
     */
    public String generateCDRReport(Subscriber subscriber, LocalDateTime start, LocalDateTime end) {
        List<CDR> cdrs = cdrRepository.findByCallerAndStartTimeAfterAndEndTimeBefore(subscriber, start, end);

        // Создаем директорию reports, если она не существует
        try {
            Files.createDirectories(Paths.get("reports"));
        } catch (IOException e) {
            e.printStackTrace();
            return "Error creating reports directory: " + e.getMessage();
        }

        String fileName = "reports/" + subscriber.getMsisdn() + "_" + UUID.randomUUID() + ".csv";

        try (FileWriter writer = new FileWriter(fileName)) {
            for (CDR cdr : cdrs) {
                writer.write(String.format("%s,%s,%s,%s,%s\n",
                        cdr.getCallType(),
                        cdr.getCaller().getMsisdn(),
                        cdr.getReceiver().getMsisdn(),
                        cdr.getStartTime().format(DateTimeFormatter.ISO_DATE_TIME),
                        cdr.getEndTime().format(DateTimeFormatter.ISO_DATE_TIME)
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error generating report: " + e.getMessage();
        }

        return "Report generated: " + fileName;
    }
}