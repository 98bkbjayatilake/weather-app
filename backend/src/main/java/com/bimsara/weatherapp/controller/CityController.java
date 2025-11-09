package com.fidenz.weatherapp.controller;

import com.fidenz.weatherapp.dto.CityWithWeatherDto;
import com.fidenz.weatherapp.model.City;
import com.fidenz.weatherapp.service.CityService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {
    
    private final CityService cityService;
    
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }
    
    @GetMapping
    public ResponseEntity<List<CityWithWeatherDto>> getAllCities(@AuthenticationPrincipal Jwt jwt) {
        // This endpoint requires authentication and returns cities with weather data
        List<CityWithWeatherDto> cities = cityService.getAllCitiesWithWeather();
        return ResponseEntity.ok(cities);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<City> getCityById(
            @PathVariable Long id,
            @AuthenticationPrincipal Jwt jwt) {
        // This endpoint requires authentication
        return cityService.getCityById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
