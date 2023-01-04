package com.williamfeliciano.blogrestapi.exception;


import com.williamfeliciano.blogrestapi.dto.ErrorDetailsDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// extends ResponseEntityExceptionHandler if you want to implement first approac of custom validation error response
@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle specific exceptions
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetailsDto> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){
        ErrorDetailsDto errorDetails = new ErrorDetailsDto( new Date(),
                exception.getMessage(), webRequest.getDescription(false));

        return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogApiException.class)
    public ResponseEntity<ErrorDetailsDto> handleBlogApiException(BlogApiException exception, WebRequest webRequest){
        ErrorDetailsDto errorDetails = new ErrorDetailsDto(new Date(),
                exception.getMessage(), webRequest.getDescription(false));

        return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
    }

    // Second Approach ** like this one better
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest webRequest){
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }
    // handle gloabl exceptions

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetailsDto> handleGloablException(Exception exception, WebRequest webRequest){
        ErrorDetailsDto errorDetails = new ErrorDetailsDto(new Date(),
                exception.getMessage(), webRequest.getDescription(false));

        return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // First approac override this method from REsponseEntityExceptionHandler interface
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//                                                                  HttpHeaders headers,
//                                                                  HttpStatusCode status,
//                                                                  WebRequest request) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) ->{
//            String fieldName = ((FieldError) error).getField();
//            String message = error.getDefaultMessage();
//            errors.put(fieldName, message);
//        });
//
//        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
//    }
}
