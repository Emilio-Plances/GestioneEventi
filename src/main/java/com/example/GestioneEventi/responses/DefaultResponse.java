package com.example.GestioneEventi.responses;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Data
public class DefaultResponse {
    private LocalDateTime responseDate;
    private Object response;
    private String message;

    public DefaultResponse(Object response, String message) {
        this.response = response;
        this.message = message;
        responseDate=LocalDateTime.now();
    }
    public DefaultResponse(String message) {
        this.message = message;
        responseDate=LocalDateTime.now();
    }
    public static ResponseEntity<DefaultResponse> full(String message, Object obj, HttpStatus httpStatus){
        DefaultResponse defaultResponse=new DefaultResponse(obj, message);
        return new ResponseEntity<>(defaultResponse,httpStatus);
    }
    public static ResponseEntity<DefaultResponse> noMessage(Object obj, HttpStatus httpStatus){
        DefaultResponse defaultResponse=new DefaultResponse(obj, httpStatus.toString());
        return new ResponseEntity<>(defaultResponse,httpStatus);
    }
    public static ResponseEntity<DefaultResponse> noObject(String message, HttpStatus httpStatus){
        DefaultResponse defaultResponse=new DefaultResponse(message);
        return new ResponseEntity<>(defaultResponse,httpStatus);
    }
}
