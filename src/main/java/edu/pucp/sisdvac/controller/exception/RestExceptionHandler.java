package edu.pucp.sisdvac.controller.exception;

import edu.pucp.sisdvac.controller.response.RestResponse;
import org.hibernate.PropertyValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);
    @ExceptionHandler(GenericException.class)
    protected ResponseEntity<?> handleGenericException(GenericException exception) {
        printErrorMessage(exception);
        return buildGenericResponse(exception, HttpStatus.FAILED_DEPENDENCY);
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<?> handleNotFoundException(NotFoundException exception) {
        printErrorMessage(exception);
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
        printErrorMessage(exception);
        return buildGenericResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InstantiationException.class)
    protected ResponseEntity<?> handleInstantiationException(InstantiationException exception) {
        printErrorMessage(exception);
        return buildGenericResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        printErrorMessage(exception);
        return buildGenericResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PropertyValueException.class)
    protected ResponseEntity<?> handlePropertyValueException(PropertyValueException exception) {
        printErrorMessage(exception);
        return buildGenericResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyAddedException.class)
    protected ResponseEntity<?> handleUserAlreadyAddedException(UserAlreadyAddedException exception) {
        printErrorMessage(exception);
        return buildGenericResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        printErrorMessage(exception);
        return buildGenericResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FormulaCalculationException.class)
    protected ResponseEntity<?> handleFormulaCalculationException(FormulaCalculationException exception) {
        printErrorMessage(exception);
        return buildGenericResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<?> buildGenericResponse(Exception exception, HttpStatus status) {
        RestResponse restResponse = RestResponse.builder()
                .status(status)
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity
                .status(restResponse.getStatus())
                .body(restResponse);
    }

    private void printErrorMessage(Exception exception) {
        LOGGER.error(String.format(
                "[%s] %s",
                exception.getClass().getName(),
                exception.getMessage()
        ));
    }
}
