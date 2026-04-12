# 📡 Common Event (Shared Event Model)

## 🚀 Overview

The **Common Event Module** is a shared library used across all microservices in the system to standardize communication via **Apache Kafka**.

Instead of maintaining multiple event classes (OrderEvent, UserEvent, ProductEvent), this module provides a **single unified event structure**.

---

## 🎯 Purpose

* Eliminate multiple event classes
* Avoid Kafka deserialization issues
* Reduce tight coupling between services
* Standardize event-driven communication
* Improve scalability and maintainability

---

## 🛠️ Tech Stack

* Java 17+
* Lombok
* Apache Kafka (used by services consuming this module)

---

## 📂 Project Structure

```plaintext
common-event
│── event
│     └── CommonEvent.java
│── constant
│     └── EventType.java
```

---

## 🧱 Common Event Model

```java
package shiroya.common.event;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonEvent {

    // Event Info
    private String eventType;

    // Order Info
    private String orderId;
    private String status;

    // User Info
    private String userId;
    private String userName;
    private String userEmail;

    // Product Info
    private String productId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;

    // Metadata
    private LocalDateTime timestamp;
}
```

---

## 🏷️ Event Types

```java
package shiroya.common.constant;

public class EventType {

    public static final String ORDER_CREATED = "ORDER_CREATED";
    public static final String USER_CREATED = "USER_CREATED";
    public static final String PRODUCT_UPDATED = "PRODUCT_UPDATED";
}
```

---

## 📩 Sample JSON

```json
{
  "eventType": "ORDER_CREATED",
  "orderId": "ORD123",
  "userId": "USR1001",
  "userName": "Satyendra",
  "userEmail": "satyendra@gmail.com",
  "productId": "P1001",
  "productName": "iPhone 15",
  "quantity": 1,
  "price": 79999.00,
  "status": "CREATED",
  "timestamp": "2026-04-12T20:45:00"
}
```

---

## 📡 Kafka Topic

| Topic Name    | Description                 |
| ------------- | --------------------------- |
| common-events | All system events published |

---

## 🔄 Event Flow

```plaintext
User Service ─┐
              ├──> Kafka (common-events) ───> Email Service
Order Service ─┤
              └──> Product Service (optional consumers)
```

---

## 🚀 Usage in Services

### 📤 Producer Example (Order Service)

```java
CommonEvent event = CommonEvent.builder()
        .eventType(EventType.ORDER_CREATED)
        .orderId(order.getId().toString())
        .userId(order.getUserId())
        .productId(order.getProductId())
        .quantity(order.getQuantity())
        .status("CREATED")
        .timestamp(LocalDateTime.now())
        .build();

kafkaTemplate.send("common-events", event);
```

---

### 📥 Consumer Example (Email Service)

```java
@KafkaListener(topics = "common-events", groupId = "created-events")
public void consume(CommonEvent event) {

    switch (event.getEventType()) {

        case EventType.USER_CREATED:
            emailService.sendUserCreatedEmail(event);
            break;

        case EventType.ORDER_CREATED:
            emailService.sendOrderEmail(event);
            break;

        case EventType.PRODUCT_UPDATED:
            emailService.sendProductEmail(event);
            break;
    }
}
```

---

## ⚙️ Kafka Configuration

```properties
spring.kafka.consumer.value-deserializer=org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
spring.kafka.consumer.properties.spring.deserializer.value.delegate.class=org.springframework.kafka.support.serializer.JsonDeserializer

spring.kafka.consumer.properties.spring.json.trusted.packages=shiroya.common.event
```

---

## ⚠️ Common Issues & Fixes

---

### ❌ Trusted Package Error

```text
not in trusted packages
```

✔️ Fix:

```properties
spring.kafka.consumer.properties.spring.json.trusted.packages=shiroya.common.event
```

---

### ❌ Deserialization Error

✔️ Fix:

* Use `ErrorHandlingDeserializer`
* Ensure same class structure in all services

---

### ❌ Invalid Topic Name

✔️ Fix:

* Avoid spaces
* Use:

```text
common-events
```

---

## 🔐 Best Practices

* Use **Enum instead of String** for eventType (recommended)
* Keep this module as a **separate Maven dependency**
* Version your event schema (v1, v2)
* Avoid sending unnecessary fields
* Keep payload lightweight

---

## 🔮 Future Enhancements

* ✅ Add Schema Registry (Avro/Protobuf)
* ✅ Versioned events
* ✅ Event validation layer
* ✅ Dead Letter Queue (DLQ)
* ✅ Retry mechanism

---

## 👨‍💻 Author

**Satyendra Chaurasiya**

---

## ⭐ Notes

* This module should be imported in:

    * Order Service
    * Product Service
    * User Service
    * Email Service
* Ensures all services speak the same event language
