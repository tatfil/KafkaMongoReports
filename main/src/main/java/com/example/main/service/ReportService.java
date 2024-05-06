package com.example.main.service;

import com.example.main.model.Report;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReportService {

    private final ProducerKafka producerKafka;
    private final ProducerSQS producerSQS;

    @Autowired
    public ReportService(ProducerKafka producer, ProducerSQS producerSQS) {
        this.producerKafka = producer;
        this.producerSQS = producerSQS;
    }

    public String createReport(Report report) throws JsonProcessingException {
        return producerKafka.sendReport(report);
    }

    public String createReportSQS(Report report) throws JsonProcessingException {
        return producerSQS.sendReport(report);
    }
}
