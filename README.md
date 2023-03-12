# Nine Article Publsher Service

## Tech:

1. Docker
2. Java 8
3. Spring-boot
4. Spring-Data-jpa
5. Swagger API doc
6. MySQL database
7. lombok and Mapstruct 


## Prerequisites

- Docker and Docker Compose are installed on your machine.
- Java installed (optional)
- Maven installed (optional) (guide to install maven https://maven.apache.org/install.html)

## Getting Started

1. Clone the repository to your local machine.
2. (Optional) If you have maven installed, then you may run this command to run the unit test and build the application jar file. 
    ```bash
   mvn clean install
3. If you dont have maven, you may directly use the default target folder where it already contains the application jar file then go to step #4.   
4. Navigate to the root directory of this application, where the docker is located.
5. Run the following command to start the application and its dependencies:

   ```bash
   docker-compose up
   
   
4. Browse the below swagger URL to your browser and you may use this directly to test the 3 Restful endpoints.
http://localhost:9191/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config#/
![alt text](https://github.com/eallanjoseph123/nine-article-publisher-service/blob/main/docs/swagger.png?raw=true)


   
## Accomplishment
1. Successfully run the application using docker.
2. Create sample unit test.
3. Used Lombok and mapstruck libraries to lessen the verbosity of the code. 
4. successfully run using the docker conatiner.
5. Applied sample documentation of the API using SWAGGER.
