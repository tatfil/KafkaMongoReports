package com.example.report.service;

import com.example.report.model.Report;
import com.example.report.model.ReportDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {

    private static final String reportTopic = "${topic.name}";

    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;
    private final ReportService reportService;

    @Autowired
    public Consumer(ObjectMapper objectMapper, ModelMapper modelMapper, ReportService reportService) {
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
        this.reportService = reportService;
    }

    @KafkaListener(topics = reportTopic)
    public void consumeMessage(String message) throws JsonProcessingException {
        log.info("message consumed {}", message);

        ReportDTO reportDTO = objectMapper.readValue(message, ReportDTO.class);
        Report report = modelMapper.map(reportDTO, Report.class);

        reportService.persistReport(report);
    }

}