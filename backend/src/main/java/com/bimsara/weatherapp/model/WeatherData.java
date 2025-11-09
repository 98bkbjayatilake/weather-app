package com.fidenz.weatherapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherData implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String cityName;
    private String country;
    private Double temperature;
    private String description;
    private Double feelsLike;
    private Integer humidity;
    private Double windSpeed;
    private String icon;
}
