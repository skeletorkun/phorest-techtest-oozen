# Appointment Service

A microservice that exposes REST endpoints

* to parse CSV files to store in-memory DB (H2)
* to list the top X number (endpoint parameter eg: 50) of clients that
    * have accumulated the most loyalty points
    * since Y date (endpoint parameter eg: 2018-01-01)
    * excluding any banned clients.
* to update, delete and fetch a single client by id

# System requirements

* Spring Boot 3.1.5
* Kotlin 1.8 / Java 17

# How to

Run the application

```
./gradlew bootRun
```

Launch H2 Console

```
http://localhost:8080/h2-console
```

### Examples

Populate the DB

* Parse CSV files separately for each resource, in-order: clients, appointments, services &/ purchases
* File size is limited to 2MB (see application.yaml)
* Some examples are below. Also, a postman collection is provided in the resources:
    * resources/postman/appointment-service-oozen.postman_collection.json

Clients:

```
curl --location 'http://localhost:8080/v1/clients/csv' \
--form 'file=@"/Users/orkun/workspace/playground/appointment-service/build/resources/test/csv/clients.csv"'
```

Appointments:

```
curl --location 'http://localhost:8080/v1/appointments/csv' \
--form 'file=@"/Users/orkun/Downloads/backend-tech-test/appointments.csv"'
```

Purchases:

```
curl --location 'http://localhost:8080/v1/purchases/csv?type=SERVICE' \
--form 'file=@"/Users/orkun/workspace/playground/appointment-service/src/test/resources/csv/services.csv"'
```

Services:

```
curl --location 'http://localhost:8080/v1/purchases/csv?type=PRODUCT' \
--form 'file=@"/Users/orkun/workspace/playground/appointment-service/build/resources/test/csv/purchases.csv"'
```

Create a new Appointment:

```
curl --location 'http://localhost:8080/v1/appointments' \
--header 'Content-Type: application/json' \
--data '{
  "clientId": "e0b8ebfc-6e57-4661-9546-328c644a3764",
  "startTime": "2016-02-07 17:15:00 +0100",
  "endTime": "2016-02-07 17:15:00 +0100"
}'
```

Retrieve an Appointment by ID:

```
curl --location 'http://localhost:8080/v1/appointments/e0b8ebfc-6e57-4661-9546-328c644a3764' \
--header 'Content-Type: application/json'
```

Retrieve Top Clients by Loyalty Points:

```
curl --location 'http://localhost:8080/v1/clients/top?size=100&sinceDate=2001-01-01' \
--header 'Content-Type: application/json'
```

# Notes on Submission

* Committed directly to main branch. Ideally we would provide a pull request to a protected master / main.  
* A note on Testing and coverage:
    * The test coverage is obviously not adequate for production but it should showcase different types such as:
        * Integration tests - IT
        * Web layer tests via MockMVC
        * Unit tests on Junit
    * We would ideally keep only a handful of end-to-end tests and have more integration tests and a lot of unit tests.

Improvement points:

* Validation is done via Kotlin null checks but no futher Validation using @NotNull / @NotBlank 
    * The CSV parser requires empty constructors to work so I had to initialize the fields in the DTOs.
    * The solution would be to use different DTOs for the CSV parsing and the REST bodies. But omitted to avoid further code bloat
* Some commits are without tests, we would normally break a feature in small PRs with good test coverage, squashed commits..etc.

  
Learning & References:
    * https://www.udemy.com/course/build-restful-apis-using-kotlin-and-spring-boot
    * https://codersee.com/upload-csv-file-in-spring-boot-rest-api-with-kotlin-and-opencsv
