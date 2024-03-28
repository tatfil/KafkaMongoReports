package com.example.report.repository;

import com.example.report.model.Report;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ReportRepository extends MongoRepository<Report, Long> {
}