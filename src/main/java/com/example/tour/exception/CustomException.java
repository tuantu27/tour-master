package com.example.tour.exception;



import com.example.tour.config.app.CustomStatus;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;

@Data
public class CustomException extends Exception {
    private int error;
    private int httpCode;
    private String errorDescription;
    private String message;
    private String threadId;
    private HttpServletRequest request;

    public CustomException(CustomStatus customStatus, String description) {
        this.error = customStatus.getValue();
        this.httpCode = customStatus.getHttpStatusCode();
        this.message = customStatus.getReasonPhrase();
        this.errorDescription = description;
    }

    public CustomException(CustomStatus customStatus) {
        this.error = customStatus.getValue();
        this.httpCode = customStatus.getHttpStatusCode();
        this.message = customStatus.getReasonPhrase();
        this.errorDescription = customStatus.getReasonPhrase();
    }
}


