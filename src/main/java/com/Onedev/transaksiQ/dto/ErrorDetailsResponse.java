package com.Onedev.transaksiQ.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorDetailsResponse {
    private LocalDateTime timestamp = LocalDateTime.now();
    private String message;
    private String details;

    public ErrorDetailsResponse(LocalDateTime timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

}
