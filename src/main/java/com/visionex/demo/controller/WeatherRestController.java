package com.visionex.demo.controller;

import com.visionex.demo.dto.WeatherSummaryDTO;
import com.visionex.demo.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

/*******************************
 *   demo
 *   Created by Vasantha Yapa
 *   12/5/2024
 *********************************/
/**
 * REST controller for handling weather-related requests.
 */
@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherRestController {

    // The weather service to fetch weather summaries
    private final WeatherService weatherService;

    /**
     * Endpoint to get weather summary for a given city.
     * @param city The name of the city to get the weather summary for.
     * @return ResponseEntity with the weather summary.
     */
    @GetMapping
    public ResponseEntity<WeatherSummaryDTO> getWeatherSummary(@RequestParam String city) throws Exception {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(weatherService.getWeatherSummary(city));
    }


}

