package com.williamfeliciano.blogrestapi.controller;

import com.williamfeliciano.blogrestapi.dto.JwtAuthResponseDto;
import com.williamfeliciano.blogrestapi.dto.LoginDto;
import com.williamfeliciano.blogrestapi.dto.RegisterDto;
import com.williamfeliciano.blogrestapi.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDto> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
        JwtAuthResponseDto authResponseDto = new JwtAuthResponseDto();
        authResponseDto.setAccessToken(token);
        return ResponseEntity.ok(authResponseDto);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(value = "/logout")
    public ResponseEntity<String> logout(@RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authHeader,
                                        HttpServletRequest request, HttpServletResponse response) {
        authService.logout(authHeader, request, response);
        return new ResponseEntity<>("Successfully logged out", HttpStatus.OK);

    }


}
