package com.williamfeliciano.blogrestapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private long id;
    @NotEmpty(message = "Name must not be null or empty")
    private String name;
    @NotEmpty(message = "Email must not be null or empty")
    @Email
    private String email;
    @NotEmpty(message = "Body must not be null or empty")
    @Size(min = 10, message = "Body should be at least 10 characters")
    private String body;
}
