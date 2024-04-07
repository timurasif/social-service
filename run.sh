#!/bin/bash

create_database() {
    echo "Creating DB 'socialDB'..."
    psql -U postgres -c "CREATE DATABASE socialDB;"
    echo "Database 'socialDB' created successfully!"

    echo "Creating tables..."
    psql -U postgres -d socialDB -c "
        CREATE TABLE images (
            image_id SERIAL PRIMARY KEY,
            image_path TEXT,
            image_format TEXT,
            created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
        );
        CREATE TABLE posts (
            id SERIAL PRIMARY KEY,
            caption TEXT,
            creator INTEGER,
            image_id INTEGER UNIQUE REFERENCES images(id),
            created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
        );
        CREATE TABLE comments (
            id SERIAL PRIMARY KEY,
            post_id INTEGER REFERENCES posts(id),
            content TEXT,
            creator INTEGER,
            created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
        );
    "
    echo "Tables created successfully!"
}

run_application() {
    echo "Building the app..."
    mvn clean package

    echo "Running the app..."
    java -jar target/social-0.0.1-SNAPSHOT.jar
}

create_database
run_application