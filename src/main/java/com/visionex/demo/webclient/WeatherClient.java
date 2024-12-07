package com.visionex.demo.webclient;

import com.visionex.demo.exception.CityNotFoundException;
import com.visionex.demo.exception.OpenWhetherMapException;
import com.visionex.demo.model.OpenWeatherMapResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

/*******************************
 *   demo
 *   Created by Vasantha Yapa
 *   12/5/2024
 *********************************/

@Component
@RequiredArgsConstructor
public class WeatherClient {

    private final WebClient.Builder webClientBuilder; // WebClient for making API requests
    private final String apiKey; // API key for authentication

    /**
     * Fetch weather data for a given city.
     * @param city the name of the city
     * @return a Mono that emits the weather response or an error
     */
    public Mono<OpenWeatherMapResponse> fetchWeatherData(String city){
        return webClientBuilder.build().get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", city)
                        .queryParam("appid", apiKey)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    if (response.statusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.error(new CityNotFoundException("City not found")); // Handle 404 errors specifically
                    }
                    return Mono.error(new OpenWhetherMapException("Open Weather API is unavailable.")); // Handle other 4xx errors
                })
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new OpenWhetherMapException("Open Weather API is unavailable."))) // Handle 5xx errors
                .bodyToMono(OpenWeatherMapResponse.class) // Parse response body to WeatherResponse
                .onErrorMap(WebClientResponseException.class, e -> {
                    if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                        return new CityNotFoundException("City not found", e); // Handle 404 error exceptions
                    }
                    return new OpenWhetherMapException("Open Weather API is unavailable.", e); // Handle other exceptions
                });
    }
}

