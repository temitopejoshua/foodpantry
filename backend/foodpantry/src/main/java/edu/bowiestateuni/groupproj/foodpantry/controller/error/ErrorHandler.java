package edu.bowiestateuni.groupproj.foodpantry.controller.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = {"edu.bowiestateuni.groupproj.foodpantry.controller"})
@Slf4j
public class ErrorHandler {
    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<APIErrorResponse> handleException(IllegalArgumentException ex) {
        log.error(ex.getLocalizedMessage());
        APIErrorResponse apiResponse = new APIErrorResponse(ex.getMessage());
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<APIErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ex.getBindingResult().getFieldErrors().forEach(error -> log.error("Field name - {} error message {} - ", error.getField(), error.getDefaultMessage())
        );
        final FieldError  fieldError = ex.getBindingResult().getFieldErrors().getFirst();

        return new ResponseEntity<>(new APIErrorResponse(String.format("[%s] - %s", fieldError.getField(), fieldError.getDefaultMessage())), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<APIErrorResponse> handleValidationExceptions(HttpMessageNotReadableException ex) {
        log.error(ex.getMessage());

        return new ResponseEntity<>(new APIErrorResponse(String.format("[%s] - %s", ex.getMessage(), ex.getMessage())), HttpStatus.BAD_REQUEST);
    }
}
