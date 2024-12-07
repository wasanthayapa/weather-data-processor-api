package com.visionex.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*******************************
 *   demo
 *   Created by Vasantha Yapa
 *   12/5/2024
 *********************************/

@Data
@AllArgsConstructor
@NoArgsConstructor
 public class OpenWeatherMapResponse {
    private String cod;
    private String message;
    private List<WeatherData> list;
    private City city;
}





















