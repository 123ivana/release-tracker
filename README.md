# Release tracker application

Service is used for tracking releases

## Technologies
- Java
- Maven
- Spring Boot
- Liquibase 
- Docker

## Getting Started
This project is build with Maven and has support for Docker builds.

To build the project locally with Maven run `./mvnw clean install`. To start the project locally run `./mvnw spring-boot:run`.

The default port is `8080`.

### Run the project locally
To start the project locally run:
```shell
./mvnw spring-boot:run
```

To start the service with service dependencies with Docker Compose you can run:
```shell
docker-compose -f docker-compose.yml up
```

#### Docker & Docker Compose
The project can also be build with Docker. To build Docker image locally please copy Maven setting.xml in root project
directory. Don't worry it will not be included in git commit

To build the image:

```shell
docker build -t release-tracker .
```

To run and build the project with Docker Compose:

```shell
docker-compose up --build
```

## Deployment
This service is deployable through a Docker image.
The latest image (`main` branch) is always available:

### Configuration

The configuration is done by environments variables.


| Environment variables name | Description                 | Default values | Possible values  |  
|----------------------------|-----------------------------|----------------|------------------|
| POSTGRESQL_HOST            | Specify postgresql host     |                | `localhost:5433` | 
| POSTGRESQL_USERNAME        | Specify postgresql username |                | postgres         |
| POSTGRESQL_PASSWORD        | Specify postgresql password |                |  postgres        | 

