package com.example.report.service;


import com.example.report.model.Report;
import com.example.report.repository.ReportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReportService {

    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public void persistReport(Report report) {
        Report persistedReport = reportRepository.save(report);
        log.info("report persisted {}", persistedReport);
    }
}
