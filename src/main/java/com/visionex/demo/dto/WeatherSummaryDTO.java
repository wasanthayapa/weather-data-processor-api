package com.visionex.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/*******************************
 *   demo
 *   Created by Vasantha Yapa
 *   12/5/2024
 *********************************/

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherSummaryDTO {
    private String city;
    private double averageTemperature;
    private String hottestDay;
    private String coldestDay;
}
