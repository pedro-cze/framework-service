# Framework Service

JavaScript framework service consists of several `REST` endpoints that enable
manipulation of `JavaScriptFramework` entities.

## Prerequisites
There are no other dependencies required. There is an embedded H2 database used to persist data among requests.
## How to build
The service is based on **Spring** Boot and **Gradle**. To build the `jar` file run following command:
```shell script
./gradlew build
```
## How to run
To run previously built fat `jar` file, run this:
```shell script
java jar ./build/libs/java-test-default-0.0.1-SNAPSHOT.jar
```
**Gradle** provides an alternative way of running the service:
```shell script
./gradlew bootRun
```
## Swagger
There is swagger-ui endpoint that serves api documentation on `/swagger-ui.html`.

## Postman collection
There is a postman collection + environment files included in /docs

### Search Note 
Search functionality allows searching for saved frameworks. Allowed predicates include:
- `:` - equal
- `>` - grater then
- `<` - less then
Search params are handled inside the body request which can have following form:
```json
[
  {
    "paramName": "name",
    "operation": ":",
    "value": "Vue JS"
  }
]
```
If there are more than one search param included inside the request, then those are combined with AND operator.
The OR operation is not supported.
