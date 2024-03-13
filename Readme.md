# Javal level-up - Bean Bank Coin 

## Environment setup
- vscode java setup: https://code.visualstudio.com/docs/java/java-tutorial
- Java_Home (Steps 2 and 4): https://www.baeldung.com/java-home-vs-path-env-var 
- maven: https://www.geeksforgeeks.org/how-to-install-apache-maven-on-windows/
- spring-boot: https://docs.spring.io/spring-cli/reference/installation.html
- filename too long error: https://hub.tcno.co/windows/tips/fix-filename-too-long/
- postman download: https://www.postman.com/downloads/
- terraform download: https://developer.hashicorp.com/terraform/install 
    > windows amd64 installer

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
    - Password – youthoughtwrong

## SQL Migrations instructions
- upload your sql to the migrations folder as a new file with this naming convention V{year}{month}{day}{24hour}{min}__{description}.sql

## Run local server and cli
- Make sure you have an env.properties file with the environment variables
### `Build`
- cd into app
- run mvn clean install
### `Run`
- cd into api
- run mvn spring-boot:run
- cd into cli 
- run mvn spring-boot:run
### `Test`
- cd into api
- run mvn test

### Postman for mock api calls
- Launch postman and import postman folder 

## Create a new release
- merge main into the release branch
- update localhost to 54.195.208.130 in the cli module
- commit and push to origin release 
- Navigate to github and select create a new release
- Select releases as your target branch

## Run official cli package from releases 
- Download elicpse-temurin jdk 21 lts {https://adoptium.net/temurin/releases/?os=alpine-linux&package=jdk&arch=x64}
- Or run mvn install
- Download the latest cli-xxx.jar file
- run java -jar {name}.jar
