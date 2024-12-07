package com.visionex.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/*******************************
 *   demo
 *   Created by Vasantha Yapa
 *   12/5/2024
 *********************************/

/**
 * Configuration class for WebClient.
 * Sets up the WebClient with the OpenWeather API base URL.
 */

@Configuration
public class WebClientConfig {

    private final OpenWeatherProperties openWeatherProperties;

    /**
     * Constructor for dependency injection of OpenWeatherProperties.
     * @param openWeatherProperties Properties containing API configuration.
     */
    public WebClientConfig(OpenWeatherProperties openWeatherProperties) {
        this.openWeatherProperties = openWeatherProperties;
    }

    /**
     * Bean for WebClient setup with the OpenWeather API base URL.
     * @return Configured WebClient instance.
     */
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder()
                .baseUrl(openWeatherProperties.getUrl());
    }

    /**
     * Bean to provide the API key.
     * @return API key string.
     */
    @Bean
    public String apiKey() {
        return openWeatherProperties.getKey();
    }
}
