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

## Endpoints

```
GET http://localhost:8080/prices?brandId=<BRAND_ID>&productId=<PRODUCT_ID>&dateApplied=<DATE_APPLIED>
```
There are no required params, but they will rise a 404 for any combination that does not give a valid return value.

Get with Example data:
```
GET http://localhost:8080/prices?brandId=1&productId=35455&dateApplied=2020-06-14T10:00:00

Response Body:
{
    "brandId": "1",
    "productId": "35455",
    "priceList": "1",
    "startDate": "2020-06-14T00:00:00",
    "endDate": "2020-12-31T23:59:59",
    "currency": "EUR",
    "price": 35.5
}
```