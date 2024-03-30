package com.example.report;

import com.example.report.repository.ReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories(basePackageClasses = ReportRepository.class)
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
public class ReportApplication {
	private static final Logger logger = LoggerFactory.getLogger(ReportApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(ReportApplication.class, args);
		logger.info("Running...");
	}
}
