# Microservices Integration Project: User, Hotel, Rating Services

This repository contains a microservices-based integration project that combines User, Hotel, and Rating services. The project focuses on providing a complete service by enabling seamless communication between the services.

## Features

- **Service Registry**: Implemented a service registry using Eureka Server for dynamic service discovery.
- **API Gateway**: Developed an API gateway using Spring Cloud Gateway to centralize routing, security, and monitoring of requests.
- **Configuration Server**: Integrated a configuration server to externalize and manage common configurations using Spring Cloud Config.
- **Authorization and Authentication**: Implemented secure access control mechanisms for the microservices using Spring Security.

## Tech Stack

- Programming Language: Java
- Framework: Spring Boot
- Database: MySQL
- Cloud Technologies: Spring Cloud
- Service Discovery: Eureka Server

### Usage

1. Start the service registry (Eureka Server).
2. Start the configuration server.
3. Start each microservice (User, Hotel, Rating) individually.
4. Access the API endpoints through the API gateway.
