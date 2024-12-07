package com.visionex.demo.service;

import com.visionex.demo.dto.WeatherSummaryDTO;
import com.visionex.demo.exception.CityNotFoundException;
import com.visionex.demo.exception.OpenWhetherMapException;
import com.visionex.demo.model.City;
import com.visionex.demo.model.GeneralData;
import com.visionex.demo.model.OpenWeatherMapResponse;
import com.visionex.demo.model.WeatherData;
import com.visionex.demo.utill.CommonUtil;
import com.visionex.demo.webclient.WeatherClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/*******************************
 *   demo
 *   Created by Vasantha Yapa
 *   12/6/2024
 *********************************/

public class WeatherServiceTest {

    @Mock
    private WeatherClient weatherClient;

    @Mock
    private CommonUtil commonUtil;

    @InjectMocks
    private WeatherServiceImpl weatherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test return weather summary according to city")
    void testWeatherSummaryByCity() throws Exception {
        // Mock OpenWeatherMapResponse
        OpenWeatherMapResponse mockResponse = createMockResponse();
        when(weatherClient.fetchWeatherData("London")).thenReturn(Mono.just(mockResponse));
        when(commonUtil.kelvinToCelsius(anyDouble())).thenAnswer(invocation -> (Double) invocation.getArgument(0) - 273.15);
         when(commonUtil.getSimpleDate("2024-12-06 12:00:00")).thenReturn("2024-12-06");
        when(commonUtil.getSimpleDate("2024-12-05 12:00:00")).thenReturn("2024-12-05");
        when(commonUtil.getSimpleDate("2024-12-07 18:00:00")).thenReturn("2024-12-07");

        // Call the method
        WeatherSummaryDTO result =  weatherService.getWeatherSummary("London");

        // Verify results
        assertNotNull(result);
        assertEquals("London", result.getCity());
        assertEquals(24.66, result.getAverageTemperature(), 0.01);
        assertEquals("2024-12-06", result.getHottestDay());
        assertEquals("2024-12-05", result.getColdestDay());

        // Verify interactions
        verify(weatherClient, times(1)).fetchWeatherData("London");
        verify(commonUtil, times(1)).kelvinToCelsius(anyDouble());
        verify(commonUtil, times(2)).getSimpleDate(anyString());
    }

    @Test
    @DisplayName("Test city not found exception")
    void testWhenCityIsUnavailableThrowsCityNotFoundException() {
        // Arrange
        String city = "unknown";
        when(weatherClient.fetchWeatherData(city)).thenReturn(Mono.error(new CityNotFoundException("City not found")));

        // Act & Assert
        Exception exception = assertThrows(CityNotFoundException.class, () -> weatherService.getWeatherSummary(city));
        assertEquals("City not found", exception.getMessage());

        // Verify
        verify(weatherClient, times(1)).fetchWeatherData(city);
    }


    @Test
    @DisplayName("Test Open weather map exception")
    void testWhenApiIsUnavailableThrowsOpenWhetherMapException() {
        // Arrange
        String city = "London";
        when(weatherClient.fetchWeatherData(city)).thenReturn(Mono.error(new OpenWhetherMapException("Open Weather API is unavailable.")));

        // Act & Assert
        Exception exception = assertThrows(OpenWhetherMapException.class, () -> weatherService.getWeatherSummary(city));
        assertEquals("Open Weather API is unavailable.", exception.getMessage());

        // Verify
        verify(weatherClient, times(1)).fetchWeatherData(city);
    }

    // Helper Method to Create Mock Response
    private OpenWeatherMapResponse createMockResponse() {
        GeneralData main1 = new GeneralData(300.15); // 27°C
        GeneralData main2 = new GeneralData(295.15); // 22°C
        GeneralData main3 = new GeneralData(298.15); // 25°C

        WeatherData data1 = new WeatherData();
        data1.setMain(main1);
        data1.setDt_txt("2024-12-06 12:00:00");

        WeatherData data2 = new WeatherData();
        data2.setMain(main2);
        data2.setDt_txt("2024-12-05 12:00:00");

        WeatherData data3 = new WeatherData();
        data3.setMain(main3);
        data3.setDt_txt("2024-12-07 18:00:00");

        OpenWeatherMapResponse openWeatherMapResponse=new OpenWeatherMapResponse();
        openWeatherMapResponse.setCity(new City("London"));
        openWeatherMapResponse.setList(Arrays.asList(data1, data2, data3));

        return openWeatherMapResponse;
    }
}
