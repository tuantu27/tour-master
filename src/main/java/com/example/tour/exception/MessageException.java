package com.example.tour.exception;

import lombok.Data;

@Data
public class MessageException {
    private long timestamp;
    private int status;
    private String error;
    private String code;
    private String message;
}
