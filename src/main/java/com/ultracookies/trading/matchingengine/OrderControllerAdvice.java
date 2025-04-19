package com.ultracookies.trading.matchingengine;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class OrderControllerAdvice  {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ErrorResponse handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpServletRequest request)
    {
        var detail = "Unable to read HTTP message";
        var rootCause = ex.getRootCause();
        if (rootCause instanceof InvalidFormatException invalidFormatException) {
            List<JsonMappingException.Reference> path = invalidFormatException.getPath();

            var fieldName = path.isEmpty() ? "unknown" : path.getFirst().getFieldName();
            var targetType = invalidFormatException.getTargetType();
            detail = String.format("Invalid value for %s. Expected one of: %s",
                    fieldName,
                    Arrays.toString(targetType.getEnumConstants()));
        }

        return ErrorResponse.builder(ex, HttpStatus.BAD_REQUEST, detail)
                .type(URI.create("http://localhost:8080/error/joemama"))
                .title("Invalid order parameter(s)")
                .instance(URI.create(request.getContextPath()))
                .property("timestamp", Instant.now())
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ErrorResponse handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            WebRequest request)
    {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.add(error.getDefaultMessage()));

        return ErrorResponse.builder(ex, HttpStatus.BAD_REQUEST, errors.size() + " violation(s).")
                .type(URI.create("http://localhost:8080/error/joemama"))
                .title("Invalid order parameter(s)")
                .instance(URI.create(request.getContextPath()))
                .property("timestamp", Instant.now())
                .property("violations", errors)
                .build();
    }
}
