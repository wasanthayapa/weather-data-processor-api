package com.visionex.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*******************************
 *   demo
 *   Created by Vasantha Yapa
 *   12/5/2024
 *********************************/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherData {
    private GeneralData main;
    private String dt_txt;
}
