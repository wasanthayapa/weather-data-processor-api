package com.visionex.demo.service;

import com.visionex.demo.dto.WeatherSummaryDTO;

import java.util.concurrent.CompletableFuture;

/*******************************
 *   demo
 *   Created by Vasantha Yapa
 *   12/5/2024
 *********************************/

/**
 * Service interface for fetching weather summaries.
 */
public interface WeatherService {

    /**
     * Fetches the weather summary for a given city.
     *
     * @param city The name of the city to get the weather summary for.
     * @return A CompletableFuture containing the WeatherSummary.
     * @throws Exception if an error occurs while fetching the weather summary.
     */
    WeatherSummaryDTO getWeatherSummary(String city) throws Exception;
}
