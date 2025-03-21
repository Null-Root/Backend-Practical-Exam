package com.example.demo.controller;

import com.example.demo.model.dto.register.LoginRequestDTO;
import com.example.demo.model.dto.register.LoginResponseDTO;
import com.example.demo.model.dto.register.RegisterRequestDTO;
import com.example.demo.model.dto.register.RegisterResponseDTO;
import com.example.demo.model.dto.login.LoginFailDTO;
import com.example.demo.model.dto.login.LoginSuccessDTO;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AuthController {

    @Autowired
    public UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> registerNewUser(
            @RequestBody RegisterRequestDTO registerRequestDTO
    ) {
        boolean status = userService.registerUser(
                registerRequestDTO.getEmail(),
                registerRequestDTO.getPassword()
        );

        if (status) {
            return ResponseEntity
                    .status(201)
                    .body(
                            RegisterResponseDTO
                                    .builder()
                                    .message("User successfully registered")
                                    .build()
                    );
        }
        return ResponseEntity
                .status(400)
                .body(
                        RegisterResponseDTO
                                .builder()
                                .message("Email already taken")
                                .build()
                );
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginExistingUser(
            @RequestBody LoginRequestDTO loginRequestDTO
    ) {
        String token = userService.loginUser(
                loginRequestDTO.getEmail(),
                loginRequestDTO.getPassword()
        );

        if (!token.isEmpty()) {
            return ResponseEntity
                    .status(201)
                    .body(
                            LoginSuccessDTO
                                    .builder()
                                    .access_token(token)
                                    .build()
                    );
        }
        return ResponseEntity
                .status(401)
                .body(
                        LoginFailDTO
                                .builder()
                                .message("invalid credentials")
                                .build()
                );
    }
}
