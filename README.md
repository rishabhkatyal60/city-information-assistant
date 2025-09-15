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
      "description": "scattered clouds",
      "temperature": 13.48,
      "feelsLike": 12.82,
      "humidity": 74,
      "icon": "03d",
      "cityName": "London"
   },
   "time": null,
   "facts": {
      "name": "London",
      "country": null,
      "description": "London is the capital and largest city of both England and the United Kingdom, with a population of 9,841,000 in 2025. Its wider metropolitan area is the largest in Western Europe, with a population of 15.1 million. London stands on the River Thames in southeast England, at the head of a 50-mile (80 km) tidal estuary down to the North Sea, and has been a major settlement for nearly 2,000 years. Its ancient core and financial centre, the City of London, was founded by the Romans as Londinium and has retained its medieval boundaries. The City of Westminster, to the west of the City of London, has been the centuries-long host of the national government and parliament. London grew rapidly in the 19th century, becoming the world's largest city at the time. Since the 19th century the name \"London\" has referred to the metropolis around the City of London, historically split between the counties of Middlesex, Essex, Surrey, Kent and Hertfordshire, which since 1965 has largely comprised the administrative area of Greater London, governed by 33 local authorities and the Greater London Authority.",
      "population": null,
      "extract": "London is the capital and largest city of both England and the United Kingdom, with a population of 9,841,000 in 2025. Its wider metropolitan area is the largest in Western Europe, with a population of 15.1 million. London stands on the River Thames in southeast England, at the head of a 50-mile (80 km) tidal estuary down to the North Sea, and has been a major settlement for nearly 2,000 years. Its ancient core and financial centre, the City of London, was founded by the Romans as Londinium and has retained its medieval boundaries. The City of Westminster, to the west of the City of London, has been the centuries-long host of the national government and parliament. London grew rapidly in the 19th century, becoming the world's largest city at the time. Since the 19th century the name \"London\" has referred to the metropolis around the City of London, historically split between the counties of Middlesex, Essex, Surrey, Kent and Hertfordshire, which since 1965 has largely comprised the administrative area of Greater London, governed by 33 local authorities and the Greater London Authority."
   },
   "error": null
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