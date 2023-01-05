package com.williamfeliciano.blogrestapi.service.serviceImpl;

import com.williamfeliciano.blogrestapi.dto.LoginDto;
import com.williamfeliciano.blogrestapi.dto.RegisterDto;
import com.williamfeliciano.blogrestapi.entity.AppUser;
import com.williamfeliciano.blogrestapi.entity.Role;
import com.williamfeliciano.blogrestapi.exception.BlogApiException;
import com.williamfeliciano.blogrestapi.repository.RoleRepository;
import com.williamfeliciano.blogrestapi.repository.UserRepository;
import com.williamfeliciano.blogrestapi.security.JwtTokenProvider;
import com.williamfeliciano.blogrestapi.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager,
                            UserRepository userRepository,
                            RoleRepository roleRepository,
                            PasswordEncoder passwordEncoder,
                            JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generateToken(authentication);

    }

    @Override
    public String register(RegisterDto registerDto) {

        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Username is already taken");
        }

        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"Email already taken");
        }
        // create the user
        AppUser user = new AppUser();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        // create the roles
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);

        // save the user
        userRepository.save(user);
        return String.format("%s has registered successfully",registerDto.getUsername());
    }

    @Override
    public void logout(String authHeader, HttpServletRequest request, HttpServletResponse response) {
        if(authHeader != null){
            String tokenValue = authHeader.replace("Bearer ", "");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }

}
