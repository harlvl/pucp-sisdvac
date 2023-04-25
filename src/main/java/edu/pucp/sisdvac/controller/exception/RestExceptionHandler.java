package edu.pucp.sisdvac.controller.exception;

import edu.pucp.sisdvac.controller.response.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(GenericException.class)
    protected ResponseEntity<?> handleGenericException(GenericException exception) {
        RestResponse restResponse = RestResponse.builder()
                .status(HttpStatus.FAILED_DEPENDENCY)
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity
                .status(restResponse.getStatus())
                .body(restResponse);
    }
    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<?> handleNotFoundException(NotFoundException exception) {
        RestResponse restResponse = RestResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .hits(0)
                .build();
        return ResponseEntity
                .status(restResponse.getStatus())
                .body(restResponse);
    }

    @ExceptionHandler(IllegalAccessException.class)
    protected ResponseEntity<?> handleIllegalAccessException(IllegalAccessException exception) {
        RestResponse restResponse = RestResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity
                .status(restResponse.getStatus())
                .body(restResponse);
    }

    @ExceptionHandler(InstantiationException.class)
    protected ResponseEntity<?> handleInstantiationException(InstantiationException exception) {
        RestResponse restResponse = RestResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity
                .status(restResponse.getStatus())
                .body(restResponse);
    }
}
