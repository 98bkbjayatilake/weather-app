package com.fidenz.weatherapp.service;

import com.fidenz.weatherapp.model.City;
import com.fidenz.weatherapp.model.WeatherData;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WeatherService {
    
    private final WebClient webClient;
    
    @Value("${weather.api.key}")
    private String apiKey;
    
    public WeatherService(WebClient webClient) {
        this.webClient = webClient;
    }
    
    @Cacheable(value = "weatherCache", key = "#cityCode")
    public WeatherData getWeatherByCityCode(String cityCode) {
        System.out.println("Fetching weather data from API for city code: " + cityCode);
        try {
            // Call OpenWeatherMap API using city code
            JsonNode response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/weather")
                            .queryParam("id", cityCode)
                            .queryParam("appid", apiKey)
                            .queryParam("units", "metric")
                            .build())
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();
            
            if (response != null) {
                WeatherData weatherData = new WeatherData();
                
                // Extract required fields: name, weather[0].description, main.temp
                weatherData.setCityName(response.path("name").asText());
                weatherData.setTemperature(response.path("main").path("temp").asDouble());
                
                if (response.path("weather").isArray() && response.path("weather").size() > 0) {
                    JsonNode weather = response.path("weather").get(0);
                    weatherData.setDescription(weather.path("description").asText());
                    weatherData.setIcon(weather.path("icon").asText());
                }
                
                // Extract additional fields
                weatherData.setCountry(response.path("sys").path("country").asText());
                weatherData.setFeelsLike(response.path("main").path("feels_like").asDouble());
                weatherData.setHumidity(response.path("main").path("humidity").asInt());
                weatherData.setWindSpeed(response.path("wind").path("speed").asDouble());
                
                return weatherData;
            }
        } catch (Exception e) {
            // Log error and return null if API fails
            System.err.println("Error fetching weather for city code " + cityCode + ": " + e.getMessage());
            return null;
        }
        
        return null;
    }
    
    @Cacheable(value = "weatherCache", key = "#city.cityCode")
    public WeatherData getWeatherForCity(City city) {
        System.out.println("Fetching weather data from API for city: " + city.getName());
        try {
            // Call OpenWeatherMap API using city code
            JsonNode response = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/weather")
                            .queryParam("id", city.getCityCode())
                            .queryParam("appid", apiKey)
                            .queryParam("units", "metric")
                            .build())
                    .retrieve()
                    .bodyToMono(JsonNode.class)
                    .block();
            
            if (response != null) {
                WeatherData weatherData = new WeatherData();
                
                // Extract required fields: name, weather[0].description, main.temp
                weatherData.setCityName(response.path("name").asText());
                weatherData.setTemperature(response.path("main").path("temp").asDouble());
                
                if (response.path("weather").isArray() && response.path("weather").size() > 0) {
                    JsonNode weather = response.path("weather").get(0);
                    weatherData.setDescription(weather.path("description").asText());
                    weatherData.setIcon(weather.path("icon").asText());
                }
                
                // Extract additional fields
                weatherData.setCountry(response.path("sys").path("country").asText());
                weatherData.setFeelsLike(response.path("main").path("feels_like").asDouble());
                weatherData.setHumidity(response.path("main").path("humidity").asInt());
                weatherData.setWindSpeed(response.path("wind").path("speed").asDouble());
                
                return weatherData;
            }
        } catch (Exception e) {
            // Log error and return mock data if API fails
            System.err.println("Error fetching weather for city " + city.getName() + ": " + e.getMessage());
            return getMockWeatherData(city);
        }
        
        return getMockWeatherData(city);
    }
    
    private WeatherData getMockWeatherData(City city) {
        // Return mock data for demonstration purposes
        WeatherData mockData = new WeatherData();
        mockData.setCityName(city.getName());
        mockData.setCountry(city.getCountry());
        mockData.setTemperature(22.5);
        mockData.setDescription("Partly cloudy");
        mockData.setFeelsLike(21.0);
        mockData.setHumidity(65);
        mockData.setWindSpeed(5.5);
        mockData.setIcon("02d");
        return mockData;
    }
}
