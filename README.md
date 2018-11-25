# jdbc_labs
JDBC Labs exerices

## Deployment

* Maven
At the root the project :
```
mvn install
mvn clean compile package ;
```
Then to launch the Simple_Query class : 
```
java -cp target/jdbc_labs-1.0-SNAPSHOT-jar-with-dependencies.jar Simple_Query
```
To launch the Command_Line_Query class : 
```
java -cp target/jdbc_labs-1.0-SNAPSHOT-jar-with-dependencies.jar Command_Line_Query "JDBC:URL:FOR:DB" myDb.Driver.Name DBUserName DBPassword "SELECT MyDBQuery From MyMind"
Example : java -cp target/jdbc_labs-1.0-SNAPSHOT-jar-with-dependencies.jar Command_Line_Query "jdbc:mysql://localhost:8889/sakila" com.mysql.jdbc.Driver root root "select last_name from actor;"
```
To launch the Reverse_Engineering class : 
```
java -cp target/jdbc_labs-1.0-SNAPSHOT-jar-with-dependencies.jar Reverse_Engineering "JDBC:URL:FOR:DB" myDb.Driver.Name DBUserName DBPassword
Example : java -cp target/jdbc_labs-1.0-SNAPSHOT-jar-with-dependencies.jar Reverse_Engineering "jdbc:mysql://localhost:8889/sakila" com.mysql.jdbc.Driver root root
```

## Built with

* Java
* Maven
* Mysql Connector J

## Requirements

* Sakila database (https://dev.mysql.com/doc/sakila/en/) imported in your MySQL Provider
* MySQL Active on Port 8889

## Author

Ali Taïga Matthias-Adams  

