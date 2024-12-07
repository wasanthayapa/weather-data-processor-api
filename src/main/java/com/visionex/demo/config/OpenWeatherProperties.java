package com.visionex.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/*******************************
 *   demo
 *   Created by Vasantha Yapa
 *   12/6/2024
 *********************************/

/**
 * Configuration properties for OpenWeather API.
 * Encapsulates the base URL and API key.
 */
@Configuration
@ConfigurationProperties(prefix = "openweather.api")
public class OpenWeatherProperties {
    private String url;
    private String key;

    // Getter for URL
    public String getUrl() {
        return url;
    }

    // Setter for URL
    public void setUrl(String url) {
        this.url = url;
    }

    // Getter for API key
    public String getKey() {
        return key;
    }

    // Setter for API key
    public void setKey(String key) {
        this.key = key;
    }
}
