# Tickets And Passes API

This is an RESTful API made with Java 11 and Spring Boot. The API can create, update and change tickets or passes for a ticket application.

## Requirements
This project use [MySQL](https://www.mysql.com/) for database, [for information how to install.](https://dev.mysql.com/doc/mysql-getting-started/en/) Scheme named '_leisure_pass_test_' must be pre-created before running the application. Or this can be changed by modifying to existing scheme under src/main/resources/application.properties .

Also [Lombok](https://projectlombok.org/) used in this project, Lombok plugin is needed to be installed for some IDEs. More information: [IntelliJ](https://projectlombok.org/setup/intellij), [Eclipse](https://projectlombok.org/setup/eclipse), [Netbeans](https://projectlombok.org/setup/netbeans).

## Assumptions
* For simplicity of the project, I assume that some customer and vendor information should be existed. Therefore, data.sql under src/main/resources/ file was created to add default information about customers and vendors. 
  * If the scheme of the database changes, it also needs to be changed in _data.sql_ as well.
* A pass can be created, updated and deleted with given vendor, city, customer and length (duration for the pass). 
* The length is between 1 hours and 336 hours (two weeks). 
* To update the pass user can also update activation time or not, this way disabled passes can be re-activated or duration for the pass can be increased. If the pass is not active then start date change to now.
* Invalid ID in any HTTP request returns Bad Request status information in JSON format.
* To delete the Pass, I assume removing from database is not ideal. So Pass object has a Boolean property for activation, when Delete Http request is made activation property will be false. Therefore the data can be used for future analysis. 

## Running the app
In order to run the app simply unzip and run LeisurePassChallengeApplication.java or or run 'mvn spring-boot:run' from the directory

### POST Request /leisure-pass/
To create a Pass, JSON fields are mandatory, as below. 

Pass Length in hours.
```JSON
{
	"PassCity":  "New York",
	"CustomerId": "3",
	"VendorId": "1",
	"PassLength": 24
}
```

### **GET Request /leisure-pass/**
Gets all the passes return in JSON format.

### **GET Request /leisure-pass/id**
Get Pass by ID returns Pass information in JSON format. Invalid ids respond status with Bad Request

### **GET Request /leisure-pass/id/activation**
Check if the Pass with given ID is still active. Return information in JSON format.

### **GET Request /leisure-pass/id/vendor/vendor_id**
Check if the Pass with given ID is for the Vendor with given ID. Return information in JSON format.

### **PUT Request /leisure-pass/update**
To update existing Pass information in JSON format is required, as below.

```JSON
{
	"PassId": "402881176f8d195b016f8d198f0c0000",
	"UpdateLength": 12,
	"UpdateActivationDate": true
}
```
Update length of the Pass and based on the UpdateActivationDate variable condition update the Pass start date.

### **DELETE Request /leisure-pass/id/deletion/**
Delete the Pass with the given ID.

## Testing
Every possible request that I can think of from the client in is tested at PassControllerTest.java file and PassService.java class methods tested on PassServiceTest.java file as well.

# Optional Challenge
In the project I use [Universally Unique Identifier (UUID)](https://en.wikipedia.org/wiki/Universally_unique_identifier). Every randomly generated UUID is unique and probability of collision is close to *"0"* that it can be negligible. In fact, the probability to find a duplicate within 103 trillion version-4 UUIDs is one in a billion. For more information: [Universally Unique Identifier (UUID)](https://en.wikipedia.org/wiki/Universally_unique_identifier).