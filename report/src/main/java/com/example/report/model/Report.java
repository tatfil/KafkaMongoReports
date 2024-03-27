package com.example.report.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String author;
    String message;
}