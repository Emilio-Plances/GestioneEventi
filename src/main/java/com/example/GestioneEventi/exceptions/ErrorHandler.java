package com.example.GestioneEventi.exceptions;


import com.example.GestioneEventi.responses.ErrorResponse;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFoundException(NotFoundException e){
        return new ErrorResponse(e.getMessage());
    }
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse unauthorizedException(UnauthorizedException e){
        return new ErrorResponse(e.getMessage());
    }
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequestException(BadRequestException e){
        return new ErrorResponse(e.getMessage());
    }
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse runtimeException(RuntimeException e){
        return new ErrorResponse(e.getMessage());
    }
    @ExceptionHandler(FullEventException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse fullEventException(FullEventException e){
        return new ErrorResponse(e.getMessage());
    }

}
