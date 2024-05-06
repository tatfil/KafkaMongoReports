package com.example.report;

//import com.example.report.repository.ReportRepository;
import com.example.report.service.Consumer;
import com.example.report.service.DocumentDBService;
import com.example.report.service.ReportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//@EnableMongoRepositories(basePackageClasses = ReportRepository.class)
@SpringBootApplication
public class ReportApplication {
	private static final Logger logger = LoggerFactory.getLogger(ReportApplication.class);
	private final ObjectMapper objectMapper = new ObjectMapper();
	private final ReportService reportService = new ReportService();
	private final DocumentDBService documentDBService = new DocumentDBService();

	public static void main(String[] args) {
		SpringApplication.run(ReportApplication.class, args);
		logger.info("Running...");

		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

		// Schedule the receiveMessages task to run every 5 seconds
		executor.scheduleAtFixedRate(new Consumer(new ObjectMapper(), new ReportService(), new DocumentDBService())::consumeMessageSQS, 0, 5, TimeUnit.SECONDS);

		// Wait indefinitely to keep the program running
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// Shutdown the executor when done
			executor.shutdown();
		}

	}
}
