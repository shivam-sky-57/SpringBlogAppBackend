package com.shivam.blog.controllers;

import com.shivam.blog.domain.dtos.AuthResponse;
import com.shivam.blog.domain.dtos.LoginRequest;
import com.shivam.blog.domain.dtos.RegisterRequest;
import com.shivam.blog.domain.dtos.UserDto;
import com.shivam.blog.domain.entities.User;
import com.shivam.blog.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        UserDetails user = authenticationService.authenticate(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );

        AuthResponse authResponse = AuthResponse.builder()
                .token(authenticationService.generateToken(user))
                .expiresIn(86400)
                .build();

        return ResponseEntity.ok(authResponse);

    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody RegisterRequest registerRequest) {
        User registeredUser = authenticationService.register(registerRequest);
        UserDto userDto = UserDto.builder()
                .id(registeredUser.getId())
                .name(registeredUser.getName())
                .email(registeredUser.getEmail())
                .createAt(registeredUser.getCreateAt())
                .build();
        return ResponseEntity.ok(userDto);
    }
}
