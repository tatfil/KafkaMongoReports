package org.example;


import com.mongodb.client.*;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Accumulators.*;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


import org.bson.Document;

import static software.amazon.awssdk.regions.Region.US_EAST_1;


public class ReportLambda implements RequestHandler<Map<String,Object>, String> {
    private static final String DOCUMENTDB_CLUSTER_ENDPOINT = "docdb-2024-04-23-19-08-32.cluster-crues2y6mwi1.us-east-1.docdb.amazonaws.com:27017";
    private static final String DOCUMENTDB_DATABASE_NAME = "report-database";
    private static final String DOCUMENTDB_COLLECTION_NAME = "training";
    private static final String S3_BUCKET_NAME = "s3tatianafilippova1112";
    private static final S3Client s3 = S3Client.builder().region(US_EAST_1).build();

    @Override
    public String handleRequest(Map<String, Object> input, Context context) {
        String template = "mongodb://%s:%s@%s/sample-database?replicaSet=rs0&readpreference=%s";
        String username = "usermongo";
        String password = "usermongo";
        String readPreference = "secondaryPreferred";
        String connectionString = String.format(template, username, password, DOCUMENTDB_CLUSTER_ENDPOINT, readPreference);

        MongoClientURI clientURI = new MongoClientURI(connectionString);
//        MongoClient mongoClient = new MongoClient(clientURI);
//
//
//        MongoDatabase testDB = mongoClient.getDatabase("report-database");
//        MongoCollection<Document> collection = testDB.getCollection("training");
//
        try (MongoClient mongoClient = new MongoClient(clientURI)) {
            // Get the database
            MongoDatabase database = mongoClient.getDatabase(DOCUMENTDB_DATABASE_NAME);

            // Get the collection
            MongoCollection<Document> collection = database.getCollection(DOCUMENTDB_COLLECTION_NAME);

            // Get the current month
            Calendar cal = Calendar.getInstance();
            int currentMonth = cal.get(Calendar.MONTH) + 1; // Month is 0-based, so add 1

            // Perform aggregation to get duration of trainings for each trainer in current month
            AggregateIterable<Document> results = collection.aggregate(
                    Arrays.asList(
                            match(and(
                                    gte("date", getFirstDayOfMonth(cal)),
                                    lt("date", getLastDayOfMonth(cal))
                            )),
                            group("$trainerName", sum("totalDuration", "$duration"))
                    )
            );

//            // Print results
//            System.out.println("Duration of trainings for each trainer in current month:");
//            for (Document result : results) {
//                String trainerName = result.getString("_id");
//                int totalDuration = result.getInteger("totalDuration");
//                System.out.println(trainerName + ": " + totalDuration + " minutes");
//            }

            StringBuilder resultString = new StringBuilder("Duration of trainings for each trainer in current month:\n");

            for (Document result : results) {
                String trainerName = result.getString("_id");
                int totalDuration = result.getInteger("totalDuration");
                resultString.append(trainerName).append(": ").append(totalDuration).append(" minutes\n");
            }

            // Get the current month
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
            String currentMonthString = dateFormat.format(new Date());

            // Upload the CSV to S3
            uploadToS3(resultString.toString(), String.valueOf(currentMonthString));

            return "Report generated and uploaded successfully for " + currentMonth;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void uploadToS3(String csvContent, String currentMonth) throws IOException {
         String key = "Trainers_Trainings_summary_" + currentMonth + ".csv";

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(S3_BUCKET_NAME)
                .key(key)
                .build();
        s3.putObject(objectRequest, RequestBody.fromString(csvContent));

        System.out.println("Report uploaded successfully to S3 with key:" + key);
    }

    // Function to get the first day of the month
    private static Date getFirstDayOfMonth(Calendar cal) {
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    // Function to get the last day of the month
    private static Date getLastDayOfMonth(Calendar cal) {
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }
}