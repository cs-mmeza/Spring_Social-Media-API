# Spring Social Media Blog API

## Overview

This project is a backend API for a hypothetical social media application where users can manage their accounts and post messages. The API is built using the Spring Boot framework, leveraging its capabilities for dependency injection, configuration, and data persistence. The project follows a micro-blogging concept, allowing users to register, log in, create, update, delete, and view messages.

## Features

- **User Registration**: Allows users to register by providing a username and password.
- **User Login**: Enables users to log in by verifying their credentials.
- **Message Creation**: Users can create new messages that are stored in the database.
- **View All Messages**: Retrieves all messages posted by any user.
- **View Messages by ID**: Retrieves a specific message by its ID.
- **Update Messages**: Allows users to update the content of a message they have posted.
- **Delete Messages**: Users can delete a message by its ID.
- **View Messages by User**: Retrieves all messages posted by a specific user.

## Database Structure

The project uses a relational database with the following tables:

### Account Table
- `accountId` (integer, primary key, auto-increment)
- `username` (varchar, unique, not null)
- `password` (varchar, not null)

### Message Table
- `messageId` (integer, primary key, auto-increment)
- `postedBy` (integer, foreign key references `Account(accountId)`)
- `messageText` (varchar, not null)
- `timePostedEpoch` (long)

## Endpoints

1. **User Registration**
   - **POST** `/register`
   - Registers a new user account. Returns the account details including `accountId`.
   - Responses:
     - `200 OK`: Registration successful.
     - `409 Conflict`: Username already exists.
     - `400 Bad Request`: Invalid registration details.

2. **User Login**
   - **POST** `/login`
   - Verifies user credentials. Returns account details if successful.
   - Responses:
     - `200 OK`: Login successful.
     - `401 Unauthorized`: Invalid credentials.

3. **Create Message**
   - **POST** `/messages`
   - Creates a new message. Returns the message details including `messageId`.
   - Responses:
     - `200 OK`: Message creation successful.
     - `400 Bad Request`: Invalid message details.

4. **Retrieve All Messages**
   - **GET** `/messages`
   - Retrieves all messages from the database.
   - Response:
     - `200 OK`: Returns a list of messages.

5. **Retrieve Message by ID**
   - **GET** `/messages/{messageId}`
   - Retrieves a message by its ID.
   - Response:
     - `200 OK`: Returns the message details.

6. **Delete Message by ID**
   - **DELETE** `/messages/{messageId}`
   - Deletes a message by its ID.
   - Response:
     - `200 OK`: Message deleted successfully.

7. **Update Message by ID**
   - **PATCH** `/messages/{messageId}`
   - Updates the content of a message identified by its ID.
   - Responses:
     - `200 OK`: Message updated successfully.
     - `400 Bad Request`: Invalid update details.

8. **Retrieve Messages by User**
   - **GET** `/accounts/{accountId}/messages`
   - Retrieves all messages posted by a specific user.
   - Response:
     - `200 OK`: Returns a list of messages posted by the user.

## Technical Requirements

- The project is built using the **Spring Boot Framework**.
- The application uses **Spring MVC** for routing and handling requests.
- **Spring Data JPA** is used for database interactions.
- The project includes functional test cases to verify correct implementation of all features.

## Getting Started

### Prerequisites
- Java 11 or higher
- Maven
- A relational database (e.g., MySQL, PostgreSQL)

### Setup

1. Clone the repository.
   ```bash
   git clone <repository_url>

### Sources
**Part of the evaluation process for:**
https://revature.com/
