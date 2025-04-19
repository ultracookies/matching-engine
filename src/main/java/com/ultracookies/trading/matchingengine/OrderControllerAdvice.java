package com.ultracookies.trading.matchingengine;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class OrderControllerAdvice  {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ErrorResponse handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex,
            WebRequest request)
    {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.add(error.getDefaultMessage()));

        return ErrorResponse.builder(ex, HttpStatus.BAD_REQUEST, errors.toString())
                .type(URI.create("http://localhost:8080/error/joemama"))
                .title("Invalid order parameters")
                .instance(URI.create(request.getContextPath()))
                .property("timestamp", Instant.now())
                .build();
    }
}
