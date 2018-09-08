# numbers.count
Spring-Boot application that aggregates and counts each unique instance within the numbers.txt file 

## Architecture

Components:

- Spring Boot Web
- Spring Data Redis
- Spring Test
- Redis embedded
- TestNG

Profiles:

- Dev: Basic execution
- Test: profile to support test execution
- Docker: Docker execution

### Endpoint:

- Name: /numbers/
- Protocol: Restful

**Resources**:

/numbers/count/{number}
- Method: POST
- Description: Add new number or increase the quantity

/numbers/count/{number}
- Method: GET
- Description: Retrieve a specific number and it quantity

/numbers/count/{number}
- Method: DELETE
- Description: Remove a specific number

/numbers/count
- Method: GET
- Description: Returns all numbers in a Map view

/numbers/count/list
- Method: GET
- Description: Returns all numbers in a List view

## Execution model:

1. Maven: 

- Access project folder
- Execute command "mvn spring-boot:run -Dspring.profiles.active=dev"


2. Test:

- Access project folder
- Option 1: Clean, Compile, Install and Test. Execute command "mvn clean install -Dspring.profiles.active=test"
- Option 2: Only test. Execute command "mvn test -Dspring.profiles.active=test"

3. Docker

- Access project folder
- Execute command "mvn clean install -DskipTest=true" to create docker image.
- Execute command "docker-compose up -d"
