# spring-rest-sandbox
Lessons Project. Sandbox repository for code which I wrote during Spring Course study (https://www.udemy.com/course/spring-alishev/).
A simple application that allows to add records into the database (table person), and get a list of records via REST requests.
It contains three endpoints for GET requests:
 - ```/api/sayHello``` - returns hello string
 - ```/people``` - returns list of records from table Person
 - ```/people/{id}``` - returns one record from table Person with the corresponding id
 
Also, application contains one endpoint for the POST request:
- ```/people``` - provides the possibility to create a new record in the table Person

Example of response for GET requests to ```/people``` endpoint:
```
  [
    {
        "name": "Viktor Mazepa",
        "age": 34,
        "email": "test@gmail.com"
    },
    {
        "name": "Tester Testerovic",
        "age": 22,
        "email": "test1@gmail.com"
    }
]
```
Example of response for GET requests to ```/people/{id}``` endpoint:
```
{
    "name": "Viktor Mazepa",
    "age": 34,
    "email": "test@gmail.com"
}
```
Example of negative response from ```/people/{id}``` endpoint:
```
{
    "message": "Person with id was not found!",
    "timestamp": "2023-04-01T16:59:40.169+00:00"
}
```
Example of POST request body to endpoint ```/people```:
```
{
    "name":"Tester Testerovic",
    "age":22,
    "email":"test1@gmail.com"
}
```
The project was created with Spring Boot, ModelMapper, and Hibernate Validator. For database collaboration, it is using Hibernate+Spring JPA.
Application using docker-compose to build application container based on tomcat-jre image and postgres image for database container. 

