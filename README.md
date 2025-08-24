# HomePlanner

Spring Boot application for managing incomes, bills, shopping and groceries.
Supports file uploads for receipts and provides monthly snapshot and forecast endpoints.

## Requirements

- Java 21
- Maven
- MySQL 8+

## Running

Configure the database connection and optional receipt storage directory in `src/main/resources/application.properties` and start the application:

```
mvn spring-boot:run
```

API documentation is available at `http://localhost:8080/swagger-ui.html` once the application is running.

### Key Endpoints

- `/api/monthly-income` – manage monthly income
- `/api/other-income` – manage other income
- `/api/bills` – manage bills and upload receipts
- `/api/groceries` – track groceries purchases and items
- `/api/shopping` – track shopping trips and items
- `/api/monthly-balance` – store monthly balances
- `/api/snapshot/{month}` – view monthly snapshot
- `/api/forecast/{month}` – view simple forecast

