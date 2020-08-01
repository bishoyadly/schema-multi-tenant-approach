package com.example.demo.exceptions;

import com.example.demo.dtos.ApiExceptionDto;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler
    public ResponseEntity handleControllerException(ApiException apiException) {
        ApiExceptionDto apiExceptionDto = new ApiExceptionDto(apiException.getStatusCode(), apiException.getMessage());
        return new ResponseEntity<>(apiExceptionDto, apiException.getStatusCode());
    }
}
