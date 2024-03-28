
# Report Processing System

## Overview
The Report Processing System is a distributed application comprised of two microservices built with Spring Boot and Kafka. The system facilitates the ingestion, processing, and storage of reports received via HTTP API, leveraging Kafka for inter-service communication and MongoDB for persistent storage.

## Microservices

### Main Microservice
- **Description**: The main microservice is responsible for receiving reports through an HTTP API and publishing them to Kafka for further processing.
- **Technologies Used**: Spring Boot, Kafka, RESTful API
- **Components**:
  - Controller: Handles incoming HTTP requests for report submission.
  - Kafka Producer: Publishes reports to Kafka topics.

### Report Microservice
- **Description**: The report microservice consumes reports from Kafka topics and saves them to MongoDB for persistent storage.
- **Technologies Used**: Spring Boot, Kafka, MongoDB
- **Components**:
  - Kafka Consumer: Subscribes to Kafka topics to receive reports for processing.
  - MongoDB Repository: Stores reports in MongoDB collections.

## Docker Configuration
- **Dockerfiles**: Contains Dockerfiles for building Docker images of each microservice.
- **Docker Compose**: Defines the Docker services required to run the entire application stack, including microservices, Kafka, and MongoDB.

## Usage
1. Clone the project repository.
2. Navigate to the project directory.
3. Run `docker-compose up` to start the application stack.
4. Access the HTTP API of the main microservice to submit reports.
5. Monitor Kafka topics and MongoDB collections for report processing and storage.
