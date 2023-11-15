# Appointment Service

A microservice that exposes REST endpoints

* to parse CSV files - up to 2MB - to store in-memory DB (H2)
* to list the top X number (endpoint parameter eg: 50) of clients that have accumulated the most loyalty points since Y date (
  endpoint parameter eg: 2018-01-01) - excluding any banned clients.
* to update one of the entities
* to fetch a single entity by id
* to delete one of the entities

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

Creating a new Appointment

```
curl --location 'http://localhost:8080/v1/appointments' \
--header 'Content-Type: application/json' \
--data '{
  "clientId": "e0b8ebfc-6e57-4661-9546-328c644a3764",
  "startTime": "2016-02-07 17:15:00 +0100",
  "endTime": "2016-02-07 17:15:00 +0100"
}'
```

Retrieving an Appointment by id

```
curl --location 'http://localhost:8080/v1/appointments/e0b8ebfc-6e57-4661-9546-328c644a3764' \
--header 'Content-Type: application/json'
```

We would expect a simple web application that would expose few REST api endpoints:

* an endpoint to consume and parse csv files and import data into some database
* an endpoint to list the top X number (endpoint parameter eg: 50) of clients that have accumulated the most loyalty points since Y date (
  endpoint parameter eg: 2018-01-01). Please exclude any banned clients.

Nice to have:

* at least one endpoint to update one of the entities
* an endpoint to fetch a single entity by id
* an endpoint to delete one of the entities

Endpoints should be designed with RESTful best practices. Request/response bodies should be in json format. Remember about the validation.

Do as much as you can in the time you have available to you. Please still submit your solution even if it's not complete. You can always add
a few notes stating what's missing and/or how you would improve the solution if you had more time.

# Notes on Submission

* Chose to implement in Kotlin instead of Java. Omitted Javadoc on methods for brevity.
* Committed directly to main branch. Ideally we would provide a pull request to a protected (master) branch.  
  branch
* A note on Testing and coverage:
    * Created only a handful tests mostly around appointments - the central object.
    * Tests on the other services and entity relationships are omitted for now for time sake.
        * AppointmentControllerTest.csvImport - an end-to-end test that covers the entire flow from controller to repository
            * with entity relationships
            * CSV import functionality
    * We would ideally keep only a handful of end-to-end tests and have more integration tests and a lot of unit tests.
    * No libs were used apart from Junit. we could use
        * Web layer Unit testing using MockMvc, Mockito or similar
        * RestAssured for integration testing
    * Ideally method names would follow a more scalable convention (e.g. If/When/Then..)

* Learning & References:
    * https://www.udemy.com/course/build-restful-apis-using-kotlin-and-spring-boot
    * https://codersee.com/upload-csv-file-in-spring-boot-rest-api-with-kotlin-and-opencsv
    *

* Some commits are without tests, ideally we could break a feature in small PRs with good test coverage, squashed commits..etc  