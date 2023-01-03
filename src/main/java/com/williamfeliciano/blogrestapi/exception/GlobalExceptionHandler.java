package com.williamfeliciano.blogrestapi.exception;


import com.williamfeliciano.blogrestapi.dto.ErrorDetailsDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

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
    // handle gloabl exceptions

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetailsDto> handleGloablException(Exception exception, WebRequest webRequest){
        ErrorDetailsDto errorDetails = new ErrorDetailsDto(new Date(),
                exception.getMessage(), webRequest.getDescription(false));

        return new ResponseEntity<>(errorDetails,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
