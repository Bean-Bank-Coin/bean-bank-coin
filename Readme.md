# Javal level-up - Bean Bank Coin 

## Environment setup
- vscode java setup: https://code.visualstudio.com/docs/java/java-tutorial
- Java_Home (Steps 2 and 4): https://www.baeldung.com/java-home-vs-path-env-var 
- maven: https://www.geeksforgeeks.org/how-to-install-apache-maven-on-windows/
- spring-boot: https://docs.spring.io/spring-cli/reference/installation.html
- filename too long error: https://hub.tcno.co/windows/tips/fix-filename-too-long/

## Connect to DB
- Download and install MySQL Workbench at Download MySQL Workbench.
- Open MySQL Workbench.            
- From Database, choose Manage Connections.
- In the Manage Server Connections window, choose New.
- In the Connect to Database window, enter the following information:
    - Enter a name for the connection – Bean-Bank-Coin-Connection
    - Hostname – bean-bank-coin-db-mysql.cbsozziiwdya.eu-west-1.rds.amazonaws.com
    - Port – 3306
    - Username – admin
    - Password –

## SQL Migrations instructions
- upload your sql to the migrations folder as a new file with this naming convention V{year}{month}{day}{24hour}{min}_{description}.sql