package com.visionex.demo.service;

import com.visionex.demo.dto.WeatherSummaryDTO;
import com.visionex.demo.exception.CityNotFoundException;
import com.visionex.demo.exception.OpenWhetherMapException;
import com.visionex.demo.model.OpenWeatherMapResponse;
import com.visionex.demo.model.WeatherData;
import com.visionex.demo.utill.CommonUtil;
import com.visionex.demo.webclient.WeatherClient;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/*******************************
 *   demo
 *   Created by Vasantha Yapa
 *   12/5/2024
 *********************************/
/**
 * Implementation of WeatherService to fetch and process weather data.
 */
@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final WeatherClient weatherClient;
    private final CommonUtil util;

    /**
     * Fetches the weather summary for a given city asynchronously and caches the result.
     * @param city the name of the city to get the weather summary for
     * @return WeatherSummary @{@link WeatherSummaryDTO}
     * @throws Exception if an error occurs while fetching the weather data
     */
    @Override
    @Cacheable(value = "weatherSummary", key = "#city", unless = "#result == null")
    @Async
    public WeatherSummaryDTO getWeatherSummary(String city) throws Exception {
        // Fetch weather data
        Mono<OpenWeatherMapResponse> weatherDataMono = weatherClient.fetchWeatherData(city);

        // Process the API response
        OpenWeatherMapResponse weatherData = weatherDataMono
                .doOnError(e -> {
                    if (e.getMessage().contains("404")) {
                        throw new CityNotFoundException("City not found");
                    }
                    if (e.getMessage().contains("500")) {
                        throw new OpenWhetherMapException("API is unavailable.");
                    }
                })
                .block();

        if (weatherData == null || weatherData.getList().isEmpty()) {
            throw new CityNotFoundException(city);
        }

        return CompletableFuture.supplyAsync(() -> processWeatherData(weatherData)).join();
    }

    /**
     * Processes the weather data to calculate the weather summary.
     * @param openWeatherMapResponse the weather data to process
     * @return the processed WeatherSummary
     */
    private WeatherSummaryDTO processWeatherData(OpenWeatherMapResponse openWeatherMapResponse) {
        double sumTemperatures = 0;
        String hottestDay = null;
        String coldestDay = null;
        double maxTemperature = Double.MIN_VALUE;
        double minTemperature = Double.MAX_VALUE;

        // Filter the last 7 days data
        List<WeatherData> last7DaysData = openWeatherMapResponse.getList().stream()
                .filter(data -> CommonUtil.isWithinLast7Days(data.getDt_txt()))
                .collect(Collectors.toList());
        // Iterate through weather data entries
        for (WeatherData weatherData : last7DaysData) {
            double temp = weatherData.getMain().getTemp();
            sumTemperatures += temp;

            // Identify hottest and coldest days
            if (temp > maxTemperature) {
                maxTemperature = temp;
                hottestDay = weatherData.getDt_txt();
            }
            if (temp < minTemperature) {
                minTemperature = temp;
                coldestDay = weatherData.getDt_txt();
            }
        }

        // calculate average Temperature
        double averageTemperature = sumTemperatures / last7DaysData.size();

        // Construct and return the WeatherSummary DTO object
        return new WeatherSummaryDTO(
                openWeatherMapResponse.getCity().getName(),
                util.kelvinToCelsius(averageTemperature),
                util.getSimpleDate(hottestDay),
                util.getSimpleDate(coldestDay)
        );
    }

}

