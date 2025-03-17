# Support System API

## 📌 Overview
The Support System API is a real-time customer support request management system built with Java 21 and Spring Boot 3. It allows efficient tracking, assignment, and resolution of support requests through a RESTful API.

## 🚀 Technologies Used

- **Java 21 (LTS)** – Ensures stability and long-term support.
- **Spring Boot 3.4.3** – Enables rapid development of REST APIs with minimal configuration.
- **Spring Data JPA** – Simplifies database interactions using Hibernate.
- **H2 Database (In-Memory)** – Lightweight and ideal for development and testing.
- **Spring Actuator** – Provides health checks and monitoring capabilities.
- **WebSockets** – Enables real-time communication between clients and agents.
- **Postman** – Used for API testing.
- **Maven 3.9.x** – Manages dependencies and builds the project.

## 🛠️ Installation & Execution

### Prerequisites
- Java 21
- Apache Maven 3.9.x
- Postman (optional for API testing)

### Clone the Repository
```sh
git clone https://github.com/username/support-system.git
cd support-system
```

### Start the Application
```sh
mvn spring-boot:run
```
The application runs on: `http://localhost:8080`

## REST API Endpoints

### 1️⃣ Add a Support Agent
**POST** `/support/add-agent`
#### Request Body (JSON):
```json
{
  "name": "Agent 1",
  "available": true
}
```
#### Response:
```json
"Agent added successfully."
```

### 2️⃣ Submit a Support Request
**POST** `/support/submit?issueDescription=TestIssue`
#### Request Body (JSON):
```json
{
  "name": "John Doe",
  "email": "johndoe@example.com"
}
```
#### Response:
```json
"Support request submitted. Request ID: <UUID>"
```

### 3️⃣ Assign a Request to an Agent
**POST** `/support/assign?requestId=<UUID>`
#### Response:
```json
"Request assigned successfully."
```

### 4️⃣ Get an Available Agent
**GET** `/support/available-agent`
#### Response:
```json
{
  "agentId": "<UUID>",
  "name": "Agent 1",
  "available": true
}
```

### 5️⃣ Send a Message
**POST** `/support/message`
#### Request Params:
- `requestId`: UUID
- `sender`: String
- `content`: String
#### Response:
```json
"Message sent and stored."
```

### 6️⃣ Upload a File
**POST** `/support/upload`
#### Form Data:
- `requestId`: `<UUID>`
- `file`: (Attach file)
#### Response:
```json
"File uploaded successfully!"
```

### 7️⃣ Retrieve Attached Files
**GET** `/support/attachments/{requestId}`
#### Response:
```json
[
  {
    "fileId": "<UUID>",
    "fileName": "filename.png",
    "filePath": "uploads/filename.png",
    "uploadedAt": "2025-03-17T03:02:17.75098"
  }
]
```

## 📂 Database Access (H2 Console)
The database is accessible through the built-in H2 Console:
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:supportdb`
- Username: `sa`
- Password: (empty)

## 📌 Troubleshooting
- If `CURL` errors appear, check that the API endpoints are correctly configured.
- If files are not visible after upload, ensure that they are being stored and served correctly.

---
For further support, refer to the project documentation or contact the development team.

