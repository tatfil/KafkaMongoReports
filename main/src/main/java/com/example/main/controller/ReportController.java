package com.example.main.controller;

import com.example.main.model.Report;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.example.main.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }


    @GetMapping("/hello")
    public String getHello(){
        return "hello";
    }


    @PostMapping
    public String createReport(@RequestBody Report report) throws JsonProcessingException {
        log.info("create report request received");
        return reportService.createReport(report);
    }
}