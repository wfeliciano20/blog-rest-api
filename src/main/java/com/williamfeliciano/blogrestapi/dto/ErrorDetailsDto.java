package com.williamfeliciano.blogrestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class ErrorDetailsDto {

    private Date timestamp;

    private String message;

    private String details;
}
