package com.fidenz.weatherapp.controller;

import com.fidenz.weatherapp.model.City;
import com.fidenz.weatherapp.model.WeatherData;
import com.fidenz.weatherapp.service.CityService;
import com.fidenz.weatherapp.service.WeatherService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    
    private final WeatherService weatherService;
    private final CityService cityService;
    
    public WeatherController(WeatherService weatherService, CityService cityService) {
        this.weatherService = weatherService;
        this.cityService = cityService;
    }
    
    @GetMapping("/city/{cityId}")
    public ResponseEntity<WeatherData> getWeatherByCityId(
            @PathVariable Long cityId,
            @AuthenticationPrincipal Jwt jwt) {
        // This endpoint requires authentication
        return cityService.getCityById(cityId)
                .map(city -> {
                    WeatherData weatherData = weatherService.getWeatherForCity(city);
                    return ResponseEntity.ok(weatherData);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/city/name/{cityName}")
    public ResponseEntity<WeatherData> getWeatherByCityName(
            @PathVariable String cityName,
            @AuthenticationPrincipal Jwt jwt) {
        // This endpoint requires authentication
        return cityService.getCityByName(cityName)
                .map(city -> {
                    WeatherData weatherData = weatherService.getWeatherForCity(city);
                    return ResponseEntity.ok(weatherData);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
