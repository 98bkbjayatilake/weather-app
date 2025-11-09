package com.fidenz.weatherapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityWithWeatherDto {
    private Long id;
    private String cityCode;
    private String name;
    private String country;
    private Double temperature;
    private String weatherDescription;
}
