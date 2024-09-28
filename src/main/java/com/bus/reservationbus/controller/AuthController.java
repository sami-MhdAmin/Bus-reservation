package com.bus.reservationbus.controller;

import com.bus.reservationbus.entities.AppUsers;
import com.bus.reservationbus.models.AuthResponseModel;
import com.bus.reservationbus.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Value("${app.jwt-expiration-milliseconds}")
    private Long expiration;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @PostMapping("login")
    public ResponseEntity<AuthResponseModel> login(@RequestBody AppUsers appUsers){
        final AuthResponseModel authResponseModel;
        final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                appUsers.getUserName(),
                appUsers.getPassword()
        )
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        authResponseModel = new AuthResponseModel(
                HttpStatus.OK.value(),
                "Successfully logged in",
                token,
                expiration,
                System.currentTimeMillis()
        );
        return ResponseEntity.ok(authResponseModel);
    }
}
