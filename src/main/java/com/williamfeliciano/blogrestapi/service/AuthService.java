package com.williamfeliciano.blogrestapi.service;

import com.williamfeliciano.blogrestapi.dto.LoginDto;
import com.williamfeliciano.blogrestapi.dto.RegisterDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
    void logout(String authHeader, HttpServletRequest request, HttpServletResponse response);
}
