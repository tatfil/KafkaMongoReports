package com.example.report.service;

import com.example.report.model.Report;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {

    private static final String reportTopic = "${topic.name}";
    private final ObjectMapper objectMapper;
    private final ReportService reportService;

    @Autowired
    public Consumer(ObjectMapper objectMapper, ReportService reportService) {
        this.objectMapper = objectMapper;
        this.reportService = reportService;
    }

    @KafkaListener(topics = reportTopic)
    public void consumeMessage(String message) throws JsonProcessingException {
        log.info("message consumed {}", message);

        Report report = objectMapper.readValue(message, Report.class);
        reportService.persistReport(report);
    }
}