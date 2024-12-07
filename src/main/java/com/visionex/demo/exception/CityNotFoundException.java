package com.visionex.demo.exception;

/*******************************
 *   demo
 *   Created by Vasantha Yapa
 *   12/5/2024
 *********************************/

public class CityNotFoundException extends RuntimeException{

    public CityNotFoundException(String message) {
        super(message);
    }

    public CityNotFoundException(String message, Throwable cause) {
        super(message,cause);
    }

}
