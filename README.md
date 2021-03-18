# HonestTrader
> Simple trading api demo.

## Table of contents
* [Build](#build)
* [Use](#use)
* [Technologies](#technologies)
* [Contact](#contact)

## Build
* Clone this repository
* Make sure you have JDK 11, Maven 2.x, MariaDb, H2
* Build with mvn clean package
* Run with java -jar target/honesttrader-0.0.1-SNAPSHOT.jar

## Use
REST: <br>
  **Security**
* POST localhost:8080/securities/create <br>
    "name" : "WSB" --> creates a Security called WSB <br>
* GET localhost:8080/securities <br>
    --> returns all available securities <br><br>
**User**
* POST localhost:8080/users/register <br>
    "username" : "Elon Musk" --> user register <br>
* GET localhost:8080/users <br>
    --> returns all user <br><br>
**Order**
* POST localhost:8080/orders/create <br>
        "securityId" : 1,<br>
	"userId" : 1,<br>
	"orderType" : "SELL",<br>
	"price" : 90,<br>
	"quantity" : 10<br>
    --> Puts a "SELL" or "BUY" order for a given security with a given price and quantity.<br>
If order could fullfilled it would return all the completed trades.<br>

## Technologies
* Java 11, Spring Boot
* MariaDb SQL
* (H2 database for testing)

## Contact
Created by Varga Bence - impszi95@gmail.com <br />
