package com.visionex.demo.handler;

import com.visionex.demo.exception.CityNotFoundException;
import com.visionex.demo.exception.OpenWhetherMapException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/*******************************
 *   demo
 *   Created by Vasantha Yapa
 *   12/5/2024
 *********************************/

/**
 * Global exception handler for managing application-wide exceptions.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle CityNotFoundException.
     * @param ex      the exception object
     * @param request the web request object
     * @return ResponseEntity with error message and HTTP status NOT_FOUND
     */
    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<?> handleCityNotFoundException(CityNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    /**
     * Handle OpenWhetherMapException.
     * @param ex      the exception object
     * @param request the web request object
     * @return ResponseEntity with error message and HTTP status NOT_FOUND
     */
    @ExceptionHandler(OpenWhetherMapException.class)
    public ResponseEntity<?> handleOpnWhetherException(OpenWhetherMapException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    /**
     * Handle generic exceptions.
     *
     * @param ex      the exception object
     * @param request the web request object
     * @return ResponseEntity with error message and HTTP status INTERNAL_SERVER_ERROR
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ex.getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

