package com.swiggy.Order.exceptions;


import com.swiggy.Order.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception, WebRequest re){
        ErrorResponse err = new ErrorResponse(LocalDateTime.now(), exception.getMessage(), re.getDescription(false));

        return new ResponseEntity<ErrorResponse>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameAlreadyInUseException.class)
    public ResponseEntity<ErrorResponse> handleUsernameAlreadyInUseException(UsernameAlreadyInUseException exception, WebRequest re){
        ErrorResponse err = new ErrorResponse(LocalDateTime.now(), exception.getMessage(), re.getDescription(false));

        return new ResponseEntity<ErrorResponse>(err, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFoundException(UserNotFoundException exception, WebRequest re){
        ErrorResponse err = new ErrorResponse(LocalDateTime.now(), exception.getMessage(), re.getDescription(false));

        return new ResponseEntity<ErrorResponse>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleResourceForbiddenException(ResourceForbiddenException exception, WebRequest re){
        ErrorResponse err = new ErrorResponse(LocalDateTime.now(), exception.getMessage(), re.getDescription(false));

        return new ResponseEntity<ErrorResponse>(err, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(GrpcServiceUnavailableException.class)
    public ResponseEntity<ErrorResponse> handleGrpcServiceUnavailableException(GrpcServiceUnavailableException exception, WebRequest re){
        ErrorResponse err = new ErrorResponse(LocalDateTime.now(), exception.getMessage(), re.getDescription(false));

        return new ResponseEntity<ErrorResponse>(err, HttpStatus.SERVICE_UNAVAILABLE);
    }

}
