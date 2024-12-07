package com.visionex.demo.exception;

/*******************************
 *   demo
 *   Created by Vasantha Yapa
 *   12/5/2024
 *********************************/

public class OpenWhetherMapException extends RuntimeException{

    public OpenWhetherMapException(String message) {
        super(message);
    }

    public OpenWhetherMapException(String message,Throwable cause) {
        super(message,cause);
    }

}
