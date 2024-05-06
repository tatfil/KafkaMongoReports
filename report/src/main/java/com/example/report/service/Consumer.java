package com.example.report.service;

import com.example.report.model.Report;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.List;

@Slf4j
@Component
public class Consumer {

    private static final String reportTopic = "${topic.name}";
    private final ObjectMapper objectMapper;
    private final ReportService reportService;

    private final DocumentDBService documentDBService;
    private static final String QUEUE_NAME = "standard-Queue";
    @Autowired
    public Consumer(ObjectMapper objectMapper, ReportService reportService, DocumentDBService documentDBService) {
        this.objectMapper = objectMapper;
        this.reportService = reportService;
        this.documentDBService = documentDBService;
    }

//    @KafkaListener(topics = reportTopic)
//    public void consumeMessage(String message) throws JsonProcessingException {
//        log.info("message consumed {}", message);
//
//        Report report = objectMapper.readValue(message, Report.class);
//        reportService.persistReport(report);
//    }


    public void consumeMessageSQS(){
        SqsClient sqsClient = SqsClient.builder()
                .region(Region.US_EAST_1)
                .build();

        try {
            GetQueueUrlRequest getQueueRequest = GetQueueUrlRequest.builder()
                    .queueName(QUEUE_NAME)
                    .build();

            String queueUrl = sqsClient.getQueueUrl(getQueueRequest).queueUrl();

            // Receive messages from the queue
            ReceiveMessageRequest receiveRequest = ReceiveMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .build();
            List<Message> messages = sqsClient.receiveMessage(receiveRequest).messages();

            // Print out the messages
            for (Message m : messages) {
                System.out.println("\n" + m.body());
            }

            for (Message m : messages) {
                documentDBService.persistReportInCluster(new Report(m.body(), m.body()));
            }


                for (Message message : messages) {
                    DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                            .queueUrl(queueUrl)
                            .receiptHandle(message.receiptHandle())
                            .build();
                    sqsClient.deleteMessage(deleteMessageRequest);
                }


        } catch (QueueNameExistsException e) {
            throw e;
        }
    }

}