# Emergency Notification System
![img](/docs/arc.png)

[![Click Me](https://via.placeholder.com/100x100.png?text=ENG)](/README.md)
[![Click Me](https://via.placeholder.com/100x100.png?text=RU)](/docs/README_RU.md)

This is a scalable and reliable emergency notification system designed to handle and deliver notifications to millions of users across various devices. One of the key features of the system is its fault tolerance and its ability to efficiently manage a large number of users thanks to its microservice architecture and the use of modern, up-to-date technologies.

# Technologies
* **Java**: Programming language for creating reliable and scalable applications.
* **Kotlin**: Modern programming language compatible with Java.
* **Gradle**: Build automation system that simplifies the process of compilation and dependency management.
* **Spring Boot**: Framework for creating and configuring performant Spring applications.
* **Spring Web**: Spring module for developing web applications and RESTful services.
* **Spring Data JPA**: Library for simplifying database interactions through Java Persistence API.
* **Spring Cloud Eureka**: Service for microservices.
* **Feign client**: Library for simplified creation of HTTP clients in Spring applications.
* **OAuth**: For secure user authorization and authentication.
* **Keycloak**: Platform for managing user authentication and authorization.
* **Lombok**: Library for automatic generation of boilerplate code.
* **Kafka**: Platform for data streaming and asynchronous messaging between services.
* **PostgreSQL**: Open-source relational database for data storage and management.
* **Java Mail**: API for sending and receiving emails in Java applications.

# Third-Party Services
* **Twilio**: Platform for sending SMS messages.
* **Google SMTP**: Service for sending emails through Google.
* **Firebase Cloud Messaging**: Service for sending notifications to Android and iOS devices.

# Features
## Microservice Architecture
The system is built on a microservice architecture, ensuring its scalability and fault tolerance. Each microservice is responsible for a specific task, making it easy to add new features and scale the system to handle high loads, supporting millions of users.
## Integration with Keycloak
Keycloak is used for managing authentication and authorization, providing secure access to services and simplifying user rights management and integration with other systems. [Example config](/recipient-service/src/main/java/dev/zvaryyka/recipientservice/config/SecurityConfig.java)
## Importing Recipients via .xlsx or .csv Format
The system allows importing recipient data from .xlsx or .csv files, making interaction with the application even more convenient. [Code](/recipient-service/src/main/java/dev/zvaryyka/recipientservice/service/RecipientService.java)
## Use of Kafka
Kafka is used for asynchronous message processing between microservices. This allows the system to process a large volume of data quickly and efficiently, ensuring low latency and high performance even with a large number of users. [Example of sending messages to topics](/notification-service/src/main/java/dev/zvaryyka/notificationservice/service/NotificationKafkaProducer.java)
## rebalancer-service Microservice
This microservice is responsible for monitoring message delivery to users. It tracks message statuses in the database and, if it detects that a message was not delivered, re-adds it to the Kafka queue for re-sending. Thus, the rebalancer-service ensures reliable delivery of all notifications, minimizing the chance of message loss and ensuring stable system operation under high load. Written in Kotlin. [Microservice](/rebalancer-service/src/main/kotlin/dev/zvaryyka/rebalancerservice)
## Interaction with Third-Party Services
Various services have been integrated for sending messages to recipients. [Microservice services](/worker-service/src/main/java/dev/zvaryyka/workerservice/service)

# Microservices
* **eureka-server**: Manages the registration and discovery of microservices.
* **api-gateway**: Processes incoming requests, directs them to appropriate microservices, and handles registration/authorization through Keycloak.
* **recipient-service**: Manages recipients, their data, and devices.
* **template-service**: Manages message templates for notifications.
* **notification-group-service**: Manages recipient groups for notifications.
* **notification-service**: Receives notification sending requests, creates tasks and sends them to Kafka, and also updates the database with this information.
* **worker-service**: Processes notification sending tasks and sends messages through external services.
* **rebalancer-service**: Monitors notification delivery statuses and initiates re-sending in case of failure.
