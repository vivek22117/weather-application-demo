package com.weather.api.exception;

public class WeatherDataNoFoundException extends RuntimeException {

    public WeatherDataNoFoundException(String message) {
        super(message);
    }
}
