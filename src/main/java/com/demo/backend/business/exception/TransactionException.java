package com.demo.backend.business.exception;

public class TransactionException extends RuntimeException{
    public TransactionException(String message) {
        super(message);
    }
}
