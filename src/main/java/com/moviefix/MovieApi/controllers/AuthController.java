package com.moviefix.MovieApi.controllers;


import com.moviefix.MovieApi.auth.entities.RefreshToken;
import com.moviefix.MovieApi.auth.entities.User;
import com.moviefix.MovieApi.auth.service.AuthService;
import com.moviefix.MovieApi.auth.service.Jwtservice;
import com.moviefix.MovieApi.auth.service.RefreshTokenService;
import com.moviefix.MovieApi.auth.utils.AuthResponse;
import com.moviefix.MovieApi.auth.utils.LoginRequest;
import com.moviefix.MovieApi.auth.utils.RefreshTokenRequest;
import com.moviefix.MovieApi.auth.utils.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth/")
public class AuthController {


    private final AuthService authService;
    private  final RefreshTokenService refreshTokenService;

    private final Jwtservice jwtservice;



    public AuthController(AuthService authService, RefreshTokenService refreshTokenService, Jwtservice jwtservice) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.jwtservice = jwtservice;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse>  refreshToken(@RequestBody RefreshTokenRequest request) {

        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(request.getRefreshToken());
        User user = refreshToken.getUser();

        String accessToken = jwtservice.generateToken(user);

        return ResponseEntity.ok(AuthResponse.builder().
                refreshToken(refreshToken.getRefreshToken()).
                accessToken(accessToken).build());

    }



}
