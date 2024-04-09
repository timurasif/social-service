# Social Service
This is a Spring Boot application creating posts, adding comments, and images in a social media platform.

## Table of Contents
1. [Introduction](#introduction)
2. [API Documentation](#api-documentation)
3. [Features](#features)
4. [Technologies Used](#technologies-used)
5. [Setup](#setup)
6. [API Endpoints](#api-endpoints)

## Introduction
The Social Service is a RESTful API-based backend application written in Java Spring Boot, designed to create and manage social media posts, comments, and images. It provides endpoints for creating, updating, and retrieving posts and comments, as well as fetching images stored in Google Cloud Storage (GCS).

## API Documentation
http://localhost:8090/swagger-ui/index.html

## Features
- Post Management: Create, update, and fetch posts.
- Comment Management: Add and delete comments on posts.
- Image Serving: Retrieve images stored in Google Cloud Storage.

## Technologies Used
- **Spring Boot**: For building the backend application.
- **Spring Data JPA (with PostgreSQL dialiect)**: For interacting with the database.
- **Spring Web**: For handling HTTP requests.
- **PostgreSQL**: As the relational database for storing posts, comments and image metadata.
- **Google Cloud Storage (GCS)**: For storing and serving image files.

## Setup
### Prerequisites
Before running the application, ensure you have the following installed:

- JDK 8 or higher
- Apache Maven
- PostgreSQL database
- Google Cloud Storage (GCS) account and credentials

### Running the Application
- Clone this repository to your local machine.
`git clone https://github.com/timurasif/social-service.git`

- Navigate to the project directory.
`cd social-service`
- Open the application.properties file located in src/main/resources directory and update the following properties with your database and GCS credentials:

application properties:
> - spring.datasource.url=jdbc:postgresql://localhost:5432/database_name
> - spring.datasource.username=database_username
> - spring.datasource.password=database_password
> - gcp.credentials.location=/path/to/your/credentials.json
> - gcp.image-bucket.name=gcs_bucket_name
 
### Run the app using shell script
`./run.sh`

OR

### Build the application using Maven
`mvn clean package`

### Run the application
`java -jar target/social-0.0.1-SNAPSHOT.jar`

## API Endpoints
The application provides the following RESTful endpoints:

### Post Endpoints:
- **POST /post**: Create a new post.
  * Provide user-id in headers
  * Provide image file with **image** key and json request body with **body** key as request body
- **PATCH /post/{id}**: Update an existing post.
  * Provide post-id as a path variable
  * Provide user-id in headers
  * Provide caption in the request body
- **GET /post**: Get posts.
  * Provide **cursor** and **length** as a path variables

### Comment Endpoints:
- **POST /comment**: Create a new comment.
  * Provide user-id in headers
  * Provide content and post_id as json request body
- **DELETE /comment/{id}**: Delete a comment.
  * Provide user-id in headers
  * Provide comment-id as a path variable

### Image Endpoint:
- **GET /image**: Get an image.
  * Provide imageUrl in json request body
