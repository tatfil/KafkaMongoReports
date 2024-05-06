package com.example.report.service;

import com.example.report.model.Report;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.stereotype.Service;

@Service
public final class DocumentDBService {
    public DocumentDBService() {
    }

    public void test(){

    }
    public void testConnectionWithCluster() {

        String template = "mongodb://%s:%s@%s/sample-database?replicaSet=rs0&readpreference=%s";
        String username = "usermongo";
        String password = "usermongo";
        String clusterEndpoint = "docdb-2024-04-23-19-08-32.cluster-crues2y6mwi1.us-east-1.docdb.amazonaws.com:27017";
        String readPreference = "secondaryPreferred";
        String connectionString = String.format(template, username, password, clusterEndpoint, readPreference);

        MongoClientURI clientURI = new MongoClientURI(connectionString);
        MongoClient mongoClient = new MongoClient(clientURI);

        MongoDatabase testDB = mongoClient.getDatabase("sample-database");
        MongoCollection<Document> numbersCollection = testDB.getCollection("sample-collection");

        Document doc = new Document("name", "pi").append("value", 3.14159);
        numbersCollection.insertOne(doc);

        try (MongoCursor<Document> cursor = numbersCollection.find().iterator()) {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        }

    }
    public void persistReportInCluster(Report report) {

        String template = "mongodb://%s:%s@%s/sample-database?replicaSet=rs0&readpreference=%s";
        String username = "usermongo";
        String password = "usermongo";
        String clusterEndpoint = "docdb-2024-04-23-19-08-32.cluster-crues2y6mwi1.us-east-1.docdb.amazonaws.com:27017";
        String readPreference = "secondaryPreferred";
        String connectionString = String.format(template, username, password, clusterEndpoint, readPreference);

        MongoClientURI clientURI = new MongoClientURI(connectionString);
        MongoClient mongoClient = new MongoClient(clientURI);

        MongoDatabase testDB = mongoClient.getDatabase("report-database");
        MongoCollection<Document> numbersCollection = testDB.getCollection("report-collection");

        Document doc = new Document("author", report.getAuthor()).append("message", report.getMessage());
        numbersCollection.insertOne(doc);

        try (MongoCursor<Document> cursor = numbersCollection.find().iterator()) {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        }

    }
}