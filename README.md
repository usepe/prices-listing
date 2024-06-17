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

## Project Structure

```
|-application
|-domain
|---exception
|---model
|---repository
|---service
|-infrastructure
|---inbound
|---outbound
|-----database
```

## Endpoints

```
GET http://localhost:8080/prices?brandId=<BRAND_ID>&productId=<PRODUCT_ID>&dateApplied=<DATE_APPLIED>
```
All params are required.

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