package com.fidenz.weatherapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fidenz.weatherapp.dto.CitiesListDto;
import com.fidenz.weatherapp.dto.CityDto;
import com.fidenz.weatherapp.dto.CityWithWeatherDto;
import com.fidenz.weatherapp.model.City;
import com.fidenz.weatherapp.model.WeatherData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class CityService {
    
    private final List<City> cities = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    private WeatherService weatherService;
    
    @Autowired
    public void setWeatherService(@Lazy WeatherService weatherService) {
        this.weatherService = weatherService;
    }
    
    @PostConstruct
    public void loadCitiesFromJson() {
        try {
            ClassPathResource resource = new ClassPathResource("cities.json");
            CitiesListDto citiesListDto = objectMapper.readValue(resource.getInputStream(), CitiesListDto.class);
            
            // Extract city codes and create City objects
            for (CityDto cityDto : citiesListDto.getList()) {
                City city = new City();
                city.setId(idGenerator.getAndIncrement());
                city.setCityCode(cityDto.getCityCode());
                city.setName(cityDto.getCityName());
                city.setCountry(""); // Country will be fetched from weather API
                cities.add(city);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load cities from JSON file", e);
        }
    }
    
    public List<City> getAllCities() {
        return new ArrayList<>(cities);
    }
    
    public Optional<City> getCityById(Long id) {
        return cities.stream()
                .filter(city -> city.getId().equals(id))
                .findFirst();
    }
    
    public Optional<City> getCityByName(String name) {
        return cities.stream()
                .filter(city -> city.getName().equalsIgnoreCase(name))
                .findFirst();
    }
    
    public List<CityWithWeatherDto> getAllCitiesWithWeather() {
        return cities.stream()
                .map(city -> {
                    CityWithWeatherDto dto = new CityWithWeatherDto();
                    dto.setId(city.getId());
                    dto.setCityCode(city.getCityCode());
                    
                    // Fetch weather data from API
                    WeatherData weatherData = weatherService.getWeatherByCityCode(city.getCityCode());
                    
                    if (weatherData != null) {
                        // Use data from API response
                        dto.setName(weatherData.getCityName());
                        dto.setCountry(weatherData.getCountry());
                        dto.setTemperature(weatherData.getTemperature());
                        dto.setWeatherDescription(weatherData.getDescription());
                    } else {
                        // Fallback to city name from JSON if API fails
                        dto.setName(city.getName());
                        dto.setCountry(city.getCountry());
                        dto.setTemperature(null);
                        dto.setWeatherDescription("Data unavailable");
                    }
                    
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
