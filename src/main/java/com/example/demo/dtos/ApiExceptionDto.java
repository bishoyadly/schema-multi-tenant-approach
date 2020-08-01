package com.example.demo.dtos;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class ApiExceptionDto {
    private HttpStatus statusCode;
    private String message;

    public ApiExceptionDto(HttpStatus statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
