package com.demo.backend.business.exception;

public class ClientException extends RuntimeException{
    public ClientException(String message) {
        super(message);
    }
}
