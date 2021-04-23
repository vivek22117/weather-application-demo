package com.weather.api.exception;

public class UserAuthenticationException extends RuntimeException {

    public UserAuthenticationException(String message) {
        super(message);
    }
}
