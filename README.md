# Request
Create a Spring-Boot application that aggregates and counts each unique instance within the https://github.com/orleibicheski/numbers.count/blob/master/numbers.txt file.

## Requirements:
- Expose an http endpoint at /numbers/count
- The result should look something like the following:

{"88":9766,"89":9778,"90":9889,"91":9956,"92":9979,"93":9962,"94":10091,"95":9855,"96":10074,"97":9895,"10":10011,"98":10087,"11":10052,"99":9980,"12":9974,"13":10187,"14":10071,"15":9930,"16":10092,"17":9801,"18":9984,"19":9971,"1":9898,"2":9919,"3":9972,"4":10073,"5":10016,"6":9969,"7":9981,"8":9934,"9":10083,"20":10042,"21":9966,"22":10158,"23":10123,"24":9961,"25":9914,"26":9939,"27":9958,"28":9882,"29":9950,"30":9952,"31":10109,"32":9994,"33":10002,"34":10102,"35":10012,"36":10098,"37":10166,"38":10030,"39":9949,"40":10064,"41":10000,"42":9947,"43":10024,"44":10113,"45":9943,"46":10122,"47":9972,"48":10003,"49":10051,"50":10039,"51":9978,"52":10003,"53":10140,"54":9925,"55":9977,"56":10015,"57":10003,"58":9972,"59":10036,"60":10075,"61":10016,"62":10010,"63":10157,"64":10097,"65":10054,"66":10005,"67":9824,"68":10296,"69":10135,"70":10021,"71":9890,"72":9881,"73":10041,"74":9855,"75":9940,"76":9955,"77":10000,"78":10088,"79":9968,"100":9980,"80":9805,"81":9962,"82":10013,"83":10067,"84":10017,"85":10011,"86":9898,"87":10075}

## Extra points:
- Sort the output.
- Add some unit tests.

## Extra Extra points:
- Give me a Docker Image.

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
