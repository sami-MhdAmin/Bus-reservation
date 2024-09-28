package com.bus.reservationbus.models;

import org.springframework.http.HttpStatus;

public class ResrvationApiExcption extends RuntimeException{
    private final HttpStatus status;
    private  final String message;

    public  ResrvationApiExcption(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
