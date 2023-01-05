package com.williamfeliciano.blogrestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthResponseDto {

    private String accessToken;

    private String tokenType = "Bearer";
}
