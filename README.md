
# **Weather Data Processor API**

## **Overview**
This Spring Boot application processes and analyzes weather data from an external API, demonstrating advanced Spring features such as asynchronous execution and caching. It fetches weather data for a given city, computes insights, and provides results via a REST API.

---

## **Features**
1. Fetches weather data using the OpenWeatherMap API.
2. Computes:
   - Average temperature over the last 7 days.
   - The hottest and coldest days in the last 7 days.
3. Caches results for each city to optimize API usage.
4. Provides a single REST endpoint for retrieving weather summaries.
5. Includes robust error handling for invalid inputs and API failures.
6. Fully tested with unit tests and mock data.

---

## **Technologies Used**
- **Java 17** (compatible with Spring Boot 3+)
- **Spring Boot**:
  - Spring Web
  - Spring Cache
  - Spring AOP
- **Testing Frameworks**:
  - JUnit 5
  - Mockito
- **Build Tool**: Maven

---

## **API Endpoint**

### **1. Fetch Weather Summary**
**Request**:  
`GET /weather?city={cityName}`

**Response**:  
Returns a JSON object with weather details:
```json
{
  "city": "London",
  "averageTemperature": 15.5,
  "hottestDay": "2024-11-20",
  "coldestDay": "2024-11-18"
}
```

**Error Responses**:
- `404 Not Found Request`: If the city name is invalid or not found.
- `500 Internal Server Error`: If an unexpected error occurs.

---

## **Setup Instructions**

### **Prerequisites**
1. Java 17 or later.
2. Maven 3.8+ installed.
3. An API key from [OpenWeatherMap](https://openweathermap.org/api).

---

### **Steps to Run**

1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd weather-data-processor
   ```

2. **Add OpenWeatherMap API Key**:
   - Open `src/main/resources/application.properties`.
   - Add your API key:
     ```properties
     openweather.api.key=YOUR_API_KEY
     ```

3. **Build the Project**:
   ```bash
   mvn clean install
   ```

4. **Run the Application**:
   ```bash
   mvn spring-boot:run
   ```

5. **Access the API**:
   - Example: `http://localhost:8080/weather?city=London`.

---

## **Testing Instructions**
1. Run the tests with:
   ```bash
   mvn test
   ```

2. Mock API responses are configured using `WireMock` for testing the service layer.

---

## **Project Structure**
```
src
├── main
│   ├── java
│   │   └── com.visonex.demo
│   │       ├── config             # Configure Caching and web client
│   │       ├── controller         # REST Controllers
│   │       ├── dto                # expose model for outside 
│   │       ├── exception          # Custom Exceptions
│   │       ├── handler            # Global Handlers
│   │       ├── model              # Data Models
│   │       ├── service            # Business Logic
│   │       ├── util               # Utilities (e.g., Temperature Converter,Date Formatter)
│   │       └── webclient          # open wethether map api client
│   └── resources
│       ├── application.properties # Configuration Properties
├── test                           # Unit and Integration Tests
└── README.md                      # Project Documentation
```

---

## **Caching Configuration**
- Results are cached using Spring Cache with an expiration time of **30 minutes**.
- Cached data is stored in memory and keyed by the city name.

---

## **Asynchronous Processing**
- Weather data is fetched and processed asynchronously to improve performance and handle multiple concurrent requests.

---

## **Known Issues**
1. Ensure the city name matches OpenWeatherMap's format to avoid errors.
2. Requires an active internet connection to fetch data from the external API.

