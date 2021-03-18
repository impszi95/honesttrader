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
<br>

## Use
REST: <br>
Security:<br>
* localhost:8080/securities/create
    *POST "name" : "WSB" --> creates a Security called WSB <br>
* localhost:8080/securities
    *GET --> returns all available securities <br>
User: <br>
* localhost:8080/users/register
    *POST "username" : "Elon Musk" --> user register
* localhost:8080/users
    *GET --> returns all user <br>
Order:
* localhost:8080/orders/create
    *POST
    	"securityId" : 1,<br>
	    "userId" : 1,<br>
	    "orderType" : "SELL",<br>
	    "price" : 90,<br>
	    "quantity" : 10<br>
    --> Puts a "SELL" or "BUY" order for a given security with a given price and quantity.
If order could fullfilled it would return all the completed trades.<br><br>

## Technologies
* Java 11, Spring Boot
* MariaDb SQL
* (H2 database for testing)

## Contact
Created by Varga Bence - impszi95@gmail.com <br />
