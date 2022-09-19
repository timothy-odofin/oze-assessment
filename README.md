# Hospital Service
Hospital migration of its locally-available data and medical records, it contains the records of all staff and patients.

## Table of Content

- [Hospital Service](#hospital-service)
    - [Table of Contents](#table-of-contents)
    - [Technologies Used](#technologies-used)
    - [Project Structure](#project-structure)
        - [Config](#config)
        - [Controller](#controller)
        - [Exception](#exception)
        - [Mapper](#mapper)
        - [Model](#model)
        - [Repository](#repository)
        - [Service](#service)
        - [Utils](#utils)
    - [Endpoints](#endpoints)
        - [Patient Endpoints](#patient-endpoints)
        - [Staff Endpoint](#staff-endpoint)
    - [Contributor](#contributor)


## General Information
### Assumption
* Docker desktop is install on the host machine [docker installation](https://www.docker.com/products/docker-desktop/)
* Docker compose should be installed, if not available in the docker desktop use [docker compose installation](https://docs.docker.com/compose/install/)
* JDK version 11+ is available on the host machine.
* Maven 3.6+ is available on the host machine

### Requirements
Database needs to be setup in dockers by running the script in the terminal using the following command, this will pull MySQl version 8 if not available in the docker
```
docker run -p 3307:3306 --name medical_store -e MYSQL_ROOT_PASSWORD=test  --restart unless-stopped -e MYSQL_DATABASE=medical_store -d mysql:8
```
The project environment variable needs to be exported into the machine host environment using the .env file present in the root directory of the project.
### Description
The application is a spring/spring-boot project which runs in a maven environment and packaged into dockers container, MySQL database was used, the project structure is explained [here](#project-structure)  
To run the project on the host machine, the following information will be required:
#### To run the project without using docker
```run
 mvn clean spring-boot:run
```
* It runs on the default port: 9898,
* The context-path is: "/medical/record"
* Access the application using _http://localhost:9898/medical/record/swagger-ui.html_
* Published postman Collection _https://documenter.getpostman.com/view/8525595/2s7Yn1d23Y#53cc8cf7-7c4d-4343-965e-b57c5bcb7234_
#### To run and deploy the application using the test script
**On Windows**
Open the run.bat file in edit mode, change the variable DEVELOPMENT_HOME to point to the location of the project.
Open the Command Line terminal and run the file
```
 run.bat
```
**On Linux**
Open the terminal and run the file named below
```
 sh run.sh
```
#### To run the integration test manually
```compile
 mvn clean verify 
```

#### To run both integration and unit test manually
```
 mvn clean build
```


The project uses a docker image called test-container for Integration testing using the db_ps.sql in the resource directory.
#### Layout
The project model layout is captured in the technology-assessment.png image in the root directory.



## Technologies Used
* Programming Language(s) and Frameworks
    * Java 11
    * Spring 5.0/Spring boot 2.7
* Dependencies
    * jackson-datatype-jsr310
    * commons-lang3
    * springfox-swagger-ui
    * spring-boot-starter-web
    * lombok
* Tools
    * IntelliJ IDEA
* Testing
    * Mockito
    * JUnit 5
    * TestContainer


## Project Structure

### Config
This contains the configuration files, it has two files:

**AppConfig** which contains the AsyncExecutor and CORS configuration of the project **SwaggerConfig** file that has the Swagger-UI configuration.
### Controller
This contains the exposed endpoints of the project

### Exception
This contains the custom exception classes of the application such as ApiError, BadRequest, ResourceNotFoundException etc.

### Mapper
This is the package that has the Mapper class that helps with converting one class from one to another.

### Model
This package has Three(3) sub-packages:
* **Entity**
  This consist of the project entity classes that is mapped to the Database.
* **DTO**
  This contains the response and request classes that is sent externally from  or to the application.


### Repository
This is the package that connects the database to the project implementation classes through the application.properties file.

### Service
This contains the class implementation and the interface classes of the application, this is where the project business logic is written.

### Utils
This contains utility classes, these classes comprise of the reusable resource.
### Validation
This is where the application validation class called the ValidUUID is written.

## Endpoints


*  ### Patient Endpoints


|Endpoints | Method | Route | Payload                     | Description                                                                                                                                                                                                                                     |
|------------|-------------|-------------------|-----------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|Add Patient | POST | patient/add | PatientRequest class        | This endpoint accept single patient request, validate the request body and save the content, NB: only the valid request would be saved. NB: Staff with the given identification number(UUID) must exists                                        |
|Delete Patient | Delete | patient/delete | staffId, dateFrom, dateTo   | Patient record with the given last_visit_date range can be deleted, if no such record exits Record not found message would be returned. NB: Staff with the given identification number(UUID) must exists                                        |
| Download Patient CSV | GET | /patient/download | staffId, patientCode           | Download a single patient record into csv, using the auto generated Id of the patient: NB: Staff with the given identification number(UUID) must exists                                                                                         |
| Download List of Patient | GET | /patient/download/bulk | minAge, staffId, page, size | This endpoint download patient with is greater than or equal to the given min age into a csv, if no patient with such age exists, a record not found message would be returned.NB: Staff with the given identification number(UUID) must exists |
|Fetch Patient | GET | patient/fetch | minAge, staffId, page,size  | This endpoint fetch patient with is greater than or equal to the given min age. NB: Staff with the given identification number(UUID) must exists                                                                                                |
| Upload Patient | POST | /patient/upload | staffId                     | This endpoint fetch patient with is greater than or equal to the given min age. NB: Staff with the given identification number(UUID) must exists                                                                                                |


* ### Staff Endpoints
| Endpoints           | Method | Route             | Payload                      | Description                                                                          |
|---------------------|--------|-------------------|------------------------------|--------------------------------------------------------------------------------------|
| Add Statff          | POST | /staff/member/add | StaffRequest class           | Endpoint for adding new staff into the system, only validated payload would be saved |
| List Staff          | GET | staff/member/fetch    | page (optional), size (optional) | Endpoint for retrieving paginated list of staff profile                              |
| Get Single staff    | GET | staff/member/retrieve | uuid | Endpoint for retrieving single staff profile                                         |
| Update Staff        | PUT |staff/member/update | uuid | Endpoint for retrieving single staff profile                                         |
| Upload Staff Photo  | POST | staff/member/upload/photograph | uuid | Endpoint for uploading staff profile image                                           |


## Testing
So far different testing has been done on the application, testing such as end-to-end testing, Unit Testing and Integrated Testing.
For the Unit testing, Mockito was used to test each controller or route, the tests are written in the test folder.
Test done on this application
* Unit Testing
* Integrated Testing
* End-to-end Testing

## Contributors
| Name      | Email | Contact | Github                                      |
|-----------|-------|---------|---------------------------------------------|
| Oyejide Odofin | odofintimothy@gmail.com | +234 7065990878 | [github](https://github.com/timothy-odofin) |
