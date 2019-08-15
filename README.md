**Tfl Schedules **

**Background:**

Application to display the next trains due to arrive

To build:
```$xslt
mvn clean package
```

To run as standalone:
Open the command prompt in the specified project folder
```$xslt
java -jar target/tfl-train-schedule-1.0.jar
```
or 

Note: To run in tomcat:
```$xslt
1. change in pom.xml  <packaging>war</packaging> insted of  <packaging>jar</packaging>
2. build the application using mvn clean package
3. deploy the war into tomcat


Once running, the rest service can either be accessed via the [SwaggerUI](http://localhost:8080/swagger-ui.html),
or can use any rest tools line postman, Advanced Rest Client...
or by curling requests at the relevant endpoints documented below.

##Note
Configure Station name in src/main/resource/application.properties file
ex: Great Portland Street Underground Station or Warren Street Underground Station


## Endpoints

```
1 ###### Search Trains for configured Station
```
GET http://localhost:8080/api/trains 
curl -X GET "http://localhost:8080/api/trains"

```
2 Example: To get platform specific trains
```
GET "http://localhost:8080/api/trains/Eastbound - Platform 2"
curl -X GET "http://localhost:8080/api/trains/Eastbound - Platform 2"
```

```
3 Open web page in Browser
```
**WEB/HTML Page**
url: http://localhost:8080/home
 
Trains Schedule Timetable
| Destination | Platform                | Arrive In |
| ----------- | ----------------------- | --------- |
| High Barnet | Northbound - Platform 1 | NOW       |
| Walthamstow | Northbound - Platform 3 | 1 min     |
| Brixton     | Southbound - Platform 4 | 1 min     |
| Walthamstow | Northbound - Platform 1 | 2 min     |
| Kennington  | Southbound - Platform 2 | 2 min     |
-----------------------------------------------------

Platform: Northbound - Platform 1
| ---------------------------------- |
    Destination | Arrive In |
| --------------| --------  |
| High Barnet   |Now        |
| High Barnet   |3 min      |
| High Barnet   |5 min      |
--------------------------------------

Platform: Southbound  - Platform 2
| ---------------------------------- |
    Destination | Arrive In |
| --------------| --------  |
| Kennington   |1 min       |
| Kennington   |9 min       |
| Kennington   |11 min      |
--------------------------------------