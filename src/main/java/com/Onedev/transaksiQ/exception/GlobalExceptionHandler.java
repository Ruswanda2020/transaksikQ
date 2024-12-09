package com.Onedev.transaksiQ.exception;

import com.Onedev.transaksiQ.dto.ErrorDetailsResponse;
import com.Onedev.transaksiQ.dto.GenericResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TransactionApiException.class)
    public ResponseEntity<GenericResponse> handleBlogAPIException(TransactionApiException e,
                                                                  WebRequest webRequest){

        ErrorDetailsResponse errorDetailsResponse =  new ErrorDetailsResponse(LocalDateTime.now(),
                e.getMessage(),
                webRequest.getDescription(false));


        GenericResponse<Map<String, String>> response = new GenericResponse<>(
                e.getErrorStatus(),
                e.getMessage(),
                null
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName =  ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        String listOfErrors = String.join(",", errors.values());

        GenericResponse<Map<String, String>> response = new GenericResponse<>(
                102,
                listOfErrors,
                null
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
