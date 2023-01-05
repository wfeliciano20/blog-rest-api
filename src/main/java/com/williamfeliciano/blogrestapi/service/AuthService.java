package com.williamfeliciano.blogrestapi.service;

import com.williamfeliciano.blogrestapi.dto.LoginDto;
import com.williamfeliciano.blogrestapi.dto.RegisterDto;

public interface AuthService {

    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}
