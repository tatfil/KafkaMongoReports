package com.example.main.service;

import com.example.main.model.Report;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReportService {

    private final Producer producer;

    @Autowired
    public ReportService(Producer producer) {
        this.producer = producer;
    }

    public String createReport(Report report) throws JsonProcessingException {
        return producer.sendReport(report);
    }
}
