package com.example.tour.exception;

import com.example.tour.config.app.CustomStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * class bat cac exception throw nem ra trong chuong trinh
 */
@RestControllerAdvice
public class ExceptionHandling {
    @Autowired
    private HttpServletRequest request;


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> handlingCustomException(CustomException customException) {
        MessageException message = new MessageException();
        message.setTimestamp(new Date().getTime());
        message.setStatus(customException.getHttpCode());
        message.setError(customException.getMessage());
        message.setMessage(customException.getErrorDescription());
        message.setCode("PL" + customException.getError());
        return ResponseEntity.status(HttpStatus.valueOf(message.getStatus())).body(message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handlingException(Exception exception) {
        MessageException message = new MessageException();
        message.setTimestamp(new Date().getTime());
        message.setStatus(CustomStatus.SOMETHING_WENT_WRONG.getHttpStatusCode());
        message.setError(CustomStatus.SOMETHING_WENT_WRONG.getReasonPhrase());
        message.setMessage(CustomStatus.SOMETHING_WENT_WRONG.getReasonPhrase());
        message.setCode("PL5000");
        return ResponseEntity.status(HttpStatus.valueOf(message.getStatus())).body(message);
    }
    private void push() {

    }
}