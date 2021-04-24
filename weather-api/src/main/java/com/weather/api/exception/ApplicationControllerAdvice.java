package com.weather.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.security.InvalidParameterException;

@ControllerAdvice
@Slf4j
public class ApplicationControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        return new ResponseEntity<>(ex.getConstraintViolations()
                .stream().findFirst()
                .get()
                .getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<String> invalidRegistrationInput(InvalidParameterException invalidParameterException) {
        log.error("Invalid registration parameters, pleas re-check");
        return new ResponseEntity<>(invalidParameterException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationError(UserAuthenticationException authenticationException) {
        log.error("Login authentication failed, please recheck credentials");
        return new ResponseEntity<>(authenticationException.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBusinessException(BusinessException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(WeatherDataNoFoundException.class)
    public ResponseEntity<String> handleNoDataFound(WeatherDataNoFoundException exception) {
        log.error("No data found");
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers, HttpStatus status,
                                                                         WebRequest request) {
        return new ResponseEntity<>("Please provide valid http method type!", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleAllUncaughtException(RuntimeException exception) {
        log.error("Unknown error occurred", exception);
        return new ResponseEntity<>("Unknown error occurred, please raise a support ticket!", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
