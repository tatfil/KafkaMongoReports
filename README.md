
# Report Processing System

## Overview
Report Processing System - AWS Migration
The Report Processing System is a distributed application that has been migrated to AWS infrastructure. The migration involves replacing MongoDB with Amazon DocumentDB for persistent storage and replacing Kafka with Amazon SQS for messaging. Additionally, report generation has been automated using AWS Lambda, which retrieves data from DocumentDB, generates reports, and publishes them to Amazon S3.

### Architecture
The AWS-based architecture consists of several components:
1. EC2 Instances:
  - Main EC2 Instance: Hosts the main microservice for receiving reports via HTTP API.
  - Bastion EC2 Instance: Provides secure access to the EC2 instances in private subnets.
  - EC2 Report Instance: Connects to the DocumentDB cluster in a private subnet and handles report generation.

2. Amazon DocumentDB Cluster: Replaces MongoDB for persistent storage of reports.

3. Amazon SQS: Replaces Kafka for messaging between microservices.

4. AWS Lambda: Automatically generates reports by fetching data from DocumentDB, processing it, and publishing the reports to Amazon S3.

### Deployment
The deployment is managed using Docker containers:
- Dockerfiles: Contains Dockerfiles for building Docker images of each microservice.
- Docker Compose: Defines the Docker services required to run the entire application stack, including microservices, SQS, and DocumentDB.

### Migration Steps
To migrate to the AWS-based architecture:
1. Clone the project repository.
2. Checkout the 'aws' branch.
3. Navigate to the project directory.
4. Run docker-compose up to start the application stack.
5. Access the HTTP API of the main microservice to submit reports.
6. Monitor Amazon SQS for message processing and Amazon DocumentDB for report storage.

Note: Ensure proper AWS IAM roles and policies are configured to enable communication between services and access control.
