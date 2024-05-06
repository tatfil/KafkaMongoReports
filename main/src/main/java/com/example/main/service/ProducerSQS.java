package com.example.main.service;

import com.example.main.model.Report;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@Component
public class ProducerSQS {

    private final ObjectMapper objectMapper;

    public ProducerSQS(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String sendReport(Report report) throws JsonProcessingException {
        String queueName = "standard-Queue";
        try (SqsClient sqsClient = SqsClient.builder()
                .region(Region.US_EAST_1)
                .build()) {
            return sendMessage(sqsClient, queueName, report.toString());
        }
    }

    public String sendMessage(SqsClient sqsClient, String queueName, String report) throws JsonProcessingException {
        String orderAsMessage = objectMapper.writeValueAsString(report);

        try {

            GetQueueUrlRequest getQueueRequest = GetQueueUrlRequest.builder()
                    .queueName(queueName)
                    .build();

            String queueUrl = sqsClient.getQueueUrl(getQueueRequest).queueUrl();
            SendMessageRequest sendMsgRequest = SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(report)
                    .delaySeconds(5)
                    .build();

            sqsClient.sendMessage(sendMsgRequest);

        } catch (SqsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }

        log.info("report produced {}", orderAsMessage);
        return "report sent";
    }
}
