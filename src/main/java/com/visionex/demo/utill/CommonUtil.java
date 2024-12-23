package com.visionex.demo.utill;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*******************************
 *   demo
 *   Created by Vasantha Yapa
 *   12/7/2024
 *********************************/
@Component
public class CommonUtil {


    // Define the date-time format for parsing
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    /**
     * Converts temperature from Kelvin to Celsius.
     * @param kelvin the temperature in Kelvin
     * @return the temperature in Celsius
     */
    public double kelvinToCelsius(double kelvin) {
        return getFormattedValue(kelvin - 273.15);
    }

    /**
     * Parses a date-time string and returns only the date part in 'yyyy-MM-dd' format.
     * @param dateTimeString the date-time string in 'yyyy-MM-dd HH:mm:ss' format
     * @return the date part as a string in 'yyyy-MM-dd' format
     */
    public String getSimpleDate(String dateTimeString) {

        // Parse the date-time string to a LocalDateTime object
        LocalDateTime dateTime = getDate(dateTimeString);
        // Extract only the date part
        LocalDate date = dateTime.toLocalDate();
        // Return the date part as a string
        return date.toString();
    }

    /**
     * Formats a double value to two decimal places using BigDecimal.
     * @param value the double value to be formatted
     * @return the formatted double value rounded to two decimal places
     */
    public double getFormattedValue(double value) {
        // Create a BigDecimal from the double value and set the scale to 2 with rounding mode HALF_UP
        BigDecimal bd = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
        // Get the rounded double value
        double roundedValue = bd.doubleValue();
        // Return the rounded value
        return roundedValue;
    }

    /**
     * Checks if the given date-time string is within the last 7 days.
     * @param dateTime the date-time string to be checked, in the format expected by the dateTimeFormatter
     * @return true if the date-time is within the last 7 days, false otherwise
     */
    public static boolean isWithinLast7Days(String dateTime) {
        // Parse the date-time string to a LocalDateTime object
        LocalDateTime weatherDate = getDate(dateTime);
        // Get the date-time for 7 days ago from now
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        // Check if the parsed date-time is not before the date-time of 7 days ago
        return !weatherDate.isBefore(sevenDaysAgo);
    }

    /**
     * Parses the given date-time string to a LocalDateTime object.
     * @param dateTime the date-time string to be parsed
     * @return the LocalDateTime object parsed from the date-time string
     */
    private static LocalDateTime getDate(String dateTime){
        return LocalDateTime.parse(dateTime, dateTimeFormatter);
    }

}
