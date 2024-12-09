package com.Onedev.transaksiQ.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TransactionApiException extends RuntimeException{

    private HttpStatus httpStatus;
    private String message;
    private int errorStatus;

    public TransactionApiException(HttpStatus httpStatus, String message, int errorStatus){
        this.httpStatus = httpStatus;
        this.message = message;
        this.errorStatus = errorStatus;
    }
}
