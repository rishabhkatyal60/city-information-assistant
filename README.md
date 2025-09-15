# City Information Assistant

A simplified Java Spring Boot application that provides **weather, time, and basic facts** about cities worldwide.

## Features

- 🌦️ Get current **weather** information for any city (via OpenWeatherMap)
- ⏰ Get local **time information** with timezone details (via WorldTimeAPI)
- 📖 Get basic **facts about cities** from Wikipedia
- ⚡ RESTful API endpoints
- 🔀 Concurrent processing for better performance
- ❤️ Health check endpoint
- 🐳 Docker support for easy deployment

## Prerequisites

- Java **17** or higher
- Maven **3.6** or higher
- OpenWeatherMap **API key** (free registration at [openweathermap.org/api](https://openweathermap.org/api))

## Setup

1. Clone this project:
   ```bash
   git clone https://github.com/rishabhkatyal60/city-information-assistant.git
   cd city-information-assistant
   ```
2. Get your OpenWeatherMap API key
3. Update `src/main/resources/application.properties`:
   ```properties
   openweather.api.key=your_actual_api_key_here
   ```

## Running the Application

```bash
# Using Maven
mvn spring-boot:run

# Or build and run JAR
mvn clean package
java -jar target/city-information-assistant-1.0.0.jar
```

The application will start on http://localhost:8080

## API Endpoints

### Health Check
```
GET http://localhost:8080/api/health
```

### Get City Information (POST)
```
POST http://localhost:8080/api/city-info
Content-Type: application/json

{
  "city": "London"
}
```

### Get City Information (GET)
```
GET http://localhost:8080/api/city-info/London
```

## Example Response

```json
{
  "city": "London",
  "weather": {
    "description": "light rain",
    "temperature": 15.5,
    "feelsLike": 14.2,
    "humidity": 78,
    "icon": "10d",
    "cityName": "London"
  },
  "time": {
    "currentTime": "2025-09-13T14:30:00+01:00",
    "timezone": "Europe/London",
    "utcOffset": "+01:00",
    "dayOfWeek": "6"
  },
  "facts": {
    "name": "London",
    "description": "London is the capital and largest city of England and the United Kingdom...",
    "extract": "London is the capital and largest city of England..."
  }
}
```

## Testing

Run tests with:
```bash
mvn test
```

## Docker Support (Optional)

Create a Dockerfile:
```dockerfile
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/city-information-assistant-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Build and run:
```bash
mvn clean package
docker build -t city-assistant .
docker run -p 8080:8080 city-assistant
```