package com.quizsaas.controller;

import com.quizsaas.dto.UserDTO;
import com.quizsaas.service.AuthService;
import com.quizsaas.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody Map<String, String> request) {
        String token = authService.login(request.get("email"), request.get("password"));
        return ResponseBuilder.success("Login successful", Map.of("token", token));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody UserDTO userDTO) {
        return ResponseBuilder.success("User registered successfully", authService.register(userDTO));
    }
}
