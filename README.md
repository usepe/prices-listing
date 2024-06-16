# Prices Listing

List prices for products for a given date.

### Built with

- Spring Boot v3.3.0
- Java 17

### Run

On a terminal, from the project root run:
```
./gradlew bootRun
```

## Architecture

### Application
Contains rest controllers that communicates with the domain via services.

### Domain
Contains domain services and models, that are the ones in charge of handling business rules and communicate with the infrastructure.

### Infrastructure
Serves as a bridge between the domain and DBs.
