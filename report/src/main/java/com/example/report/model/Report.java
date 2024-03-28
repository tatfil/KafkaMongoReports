package com.example.report.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "reports")
public class Report {


    @Id
    private String id;
    String author;
    String message;

    public Report() {
    }

    public Report(String author, String message) {
        this.author = author;
        this.message = message;
    }
}