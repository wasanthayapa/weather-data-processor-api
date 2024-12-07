package com.visionex.demo.controller;

import com.visionex.demo.dto.WeatherSummaryDTO;
import com.visionex.demo.exception.CityNotFoundException;
import com.visionex.demo.exception.OpenWhetherMapException;
import com.visionex.demo.service.WeatherService;
import com.visionex.demo.service.WeatherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.concurrent.CompletableFuture;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/*******************************
 *   demo
 *   Created by Vasantha Yapa
 *   12/6/2024
 *********************************/

@WebMvcTest(WeatherRestController.class)
class WeatherRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test return weather summary")
    void testReturnWeatherSummary() throws Exception {
        // Arrange
        String city = "London";
        WeatherSummaryDTO mockSummary = new WeatherSummaryDTO(
                "London", 25.0, "2024-12-06", "2024-12-05"
        );
        ResponseEntity<WeatherSummaryDTO> responseEntity=ResponseEntity
                .status(HttpStatus.OK)
                .body(mockSummary);
        given(weatherService.getWeatherSummary(anyString())).willReturn(mockSummary);
        // Act & Assert
        mockMvc.perform(get("/weather")
                .param("city", city)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(result -> System.out.println("Response: " + result.getResponse().getContentAsString()))
                .andExpect(jsonPath("$.city").value("London"))
                .andExpect(jsonPath("$.averageTemperature").value(25.0))
                .andExpect(jsonPath("$.hottestDay").value("2024-12-06"))
                .andExpect(jsonPath("$.coldestDay").value("2024-12-05"));

        verify(weatherService, times(1)).getWeatherSummary(city);
    }

    @Test
    @DisplayName("Test throw city not found")
    void testCityNotFound() throws Exception {
        given(weatherService.getWeatherSummary(anyString())).willThrow(new CityNotFoundException("City not found"));
        mockMvc.perform(get("/weather")
                .param("city", "UnknownCity"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").value("City not found"));
        verify(weatherService, times(1)).getWeatherSummary(anyString());

    }

    @Test
    @DisplayName("Test throw open whether map not available")
    void testOpenWhetherMapException() throws Exception {
        given(weatherService.getWeatherSummary(anyString())).willThrow(new OpenWhetherMapException("Open Weather API is unavailable."));
        mockMvc.perform(get("/weather")
                .param("city", "UnknownCity"))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$").value("Open Weather API is unavailable."));

        verify(weatherService, times(1)).getWeatherSummary(anyString());
    }
}
