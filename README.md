
# CACHI Health Platform - Backend

This repository contains the backend service for the CACHI (Culturally Appropriate Cancer Health Improvement) Platform. It is built with **Java** and **Spring Boot**.

Its primary responsibility is to serve as an API gateway for the AI-assisted Chatbot. It receives user queries from the React frontend, processes the retrieval-augmented generation (RAG) logic, communicates with the external LLM API (OpenRouter), and returns the simplified health information to the user.

## 📂 Project Structure

The project follows a standard Spring Boot layered architecture:

```text
src/main/java/com/example/cachibackend/
 ├── config/       # Global configuration 
 ├── controller/   # REST API endpoints
 ├── model/        # Data Transfer Objects (DTOs) for request/response bodies
 └── service/      # Business logic and external LLM API integration

```

## ⚙️ Configuration & Changing the LLM API Key

The application relies on external LLM services (via OpenRouter) to power the chatbot. **Before running the application, you MUST configure your own API Key.**

1. Navigate to the `src/main/resources/` directory.
2. Open or create the `application.properties` file.
3. Update the file with your specific configurations.

Here is an example of what your `application.properties` should look like:

```properties
# Server Configuration
server.port=8888
spring.application.name=cachi-backend

# LLM API Configuration (OpenRouter / OpenAI format)
# Replace 'YOUR_API_KEY_HERE' with your actual API key applied from the provider.
openrouter.api.key=YOUR_API_KEY_HERE


```

> **Security Note:** Never commit your actual API keys to public version control. If you are using Git, ensure your `.gitignore` or environment variables are handled properly in production.

## 🚀 Quick Start Guide

### Prerequisites

* **Java:** JDK 17 or higher
* **Maven:** 3.6+ (Alternatively, you can use the included Maven wrapper `mvnw`)

### Running Locally

1. **Clone the repository:**
```bash
git clone <your-backend-repo-url>
cd cachi-backend

```


2. **Configure the API Key:**
   Ensure you have updated the `application.properties` file as described in the Configuration section.
3. **Build and Run the Application:**
   Using the Maven wrapper:
```bash
# For Windows
mvnw.cmd spring-boot:run

# For Mac/Linux
./mvnw spring-boot:run

```


*Alternatively, if you have Maven installed globally, you can simply run:*
```bash
mvn spring-boot:run

```


4. **Verify it's running:**
   The server should start on port `8888` (or your configured port). The backend is now ready to accept POST requests from the React frontend at endpoints like `http://localhost:8888/api/chat`.