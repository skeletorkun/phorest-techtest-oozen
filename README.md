# Appointment Service

*Comb as You Are* have decided to ditch their old salon software provider
and upgrade to *Phorest* to avail of its best in class client retention tools.
They're excited to finally offer their clients the opportunity to book online.

They've exported their clients appointment data from their old provider and
would like to email their top 50 most loyal clients of the past year with the news
that they can now book their next appointment online.

# System requirements

* Spring Boot 3.1.5
* Kotlin 1.8 / Java 17
*

# Structure

The exported data is split across 4 files.

* clients.csv
* appointments.csv
* services.csv
* purchases.csv

Each client has many appointments and are related through a `client_id` property on the appointment
Each appointment has many services and are related through an `appointment_id` property on the service
Each appointment has 0 or many purchases and are related through an `appointment_id` property on the purchase
Services and purchases have an associated number of loyalty points defined as a property
Clients have a boolean banned property defined on the client

# Service at a glance

Running the application

```
./gradlew bootRun
```

H2 Console

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
curl --location 'http://localhost:8080/v1/appointments/123' \
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

## Testing

We would prefer to see a partial solution which is accompanied by tests, than a fully working solution without tests.

# Notes on Submission

* Chose to implement in Kotlin instead of Java.
* Committed directly to main branch. Ideally we would provide a pull request to a protected (master) branch.  
  branch

Please submit your solution in the form of a link to a public source control repository which contains your code e.g Github, Gitlab etc.
Ideally we would like to see the development progress by viewing commits history.