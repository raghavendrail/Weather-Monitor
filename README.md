## Features Implemented

- The system continuously calls the OpenWeatherMap API at a configurable interval (e.g., every 5 minutes) to retrieve real-time weather data for major metros in India, including:
  - Delhi
  - Mumbai
  - Chennai
  - Bangalore
  - Kolkata
  - Hyderabad

- For each received weather update, the system converts temperature values from Kelvin to Celsius. This conversion can also accommodate user preferences for temperature units.

## Rollups and Aggregates

1. **Daily Weather Summary:**
   - Roll up the weather data for each day.
   - Calculate daily aggregates for:
     - Average temperature
     - Maximum temperature
     - Minimum temperature
     - Dominant weather condition (provide reason for this)
   - Store the daily summaries in a database or persistent storage for further analysis.

2. **Alerting Thresholds:**
   - Define user-configurable thresholds for temperature or specific weather conditions (e.g., alert if temperature exceeds 35 degrees Celsius for two consecutive updates).
   - Continuously track the latest weather data and compare it with the thresholds.
   - If a threshold is breached, trigger an alert for the current weather conditions. Alerts could be displayed on the console or sent through an email notification system (implementation details left open-ended).

3. **Implement Visualizations:**
   - Display daily weather summaries, historical trends, and triggered alerts.

## Test Cases

1. **System Setup:**
   - Verify that the system starts successfully and connects to the OpenWeatherMap API using a valid API key.

2. **Data Retrieval:**
   - Simulate API calls at configurable intervals.
   - Ensure that the system retrieves weather data for the specified location and parses the response correctly.

3. **Temperature Conversion:**
   - Test the conversion of temperature values from Kelvin to Celsius (or Fahrenheit) based on user preference.

4. **Daily Weather Summary:**
   - Simulate a sequence of weather updates for several days.
   - Verify that daily summaries are calculated correctly, including average, maximum, minimum temperatures, and dominant weather condition.

5. **Alerting Thresholds:**
   - Define and configure user thresholds for temperature or weather conditions.
   - Simulate weather data exceeding or breaching the thresholds.
   - Verify that alerts are triggered only when a threshold is violated.
## Build Instructions

### Prerequisites
- Eclipse IDE
- MySQL
- Postman for API testing

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/raghavendrail/Weather-Monitoring.git
   cd Weather-Monitor
## Dependencies

To set up and run the Weather Monitoring application, ensure you have the following software and libraries:

### 1. Software Requirements

- **Java JDK 17**: Download it from the [Oracle website](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html) or use an open-source alternative like [OpenJDK](https://openjdk.java.net/).
- **Maven**: Make sure you have Apache Maven installed. You can download it from the [Maven website](https://maven.apache.org/download.cgi).
  
### 2. Database

- **MySQL**: The application uses MySQL for data storage. You can install MySQL locally or use Docker to run it as a container.
- user your own username and password in place of my username and password in application.properties

# API Configuration
weather.api.key=e5331d93884e6ee398563a2a6d56114e
weather.api.url=https://api.openweathermap.org/data/2.5/weather
logging.level.org.springframework=INFO

-here change api key (generated from your profile in openweathermap)

#Alert 
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=raghashetty30@gmail.com  (your user mail)
spring.mail.password=zkuczahtqybbbciz //code from app password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

#### Using Docker to Run MySQL

If you want to use Docker, run the following command to start a MySQL container:

```bash
docker run --name mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=weather_db -p 3306:3306 -d mysql:latest

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt</artifactId>
        <version>0.9.1</version> <!-- Check for the latest version -->
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-mail</artifactId>
    </dependency>
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-logging</artifactId>
    </dependency>
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-core</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
