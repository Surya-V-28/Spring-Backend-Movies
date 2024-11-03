package com.moviefix.MovieApi.auth.service;


import com.moviefix.MovieApi.auth.entities.User;
import com.moviefix.MovieApi.auth.entities.UserRole;
import com.moviefix.MovieApi.auth.repository.UserRepository;
import com.moviefix.MovieApi.auth.utils.AuthResponse;
import com.moviefix.MovieApi.auth.utils.LoginRequest;
import com.moviefix.MovieApi.auth.utils.RegisterRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class AuthService {

    private  final UserRepository userRepository;
    private  final PasswordEncoder passwordEncoder;
    private final Jwtservice jwtservice;
    private  final  RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, Jwtservice jwtservice, RefreshTokenService refreshTokenService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtservice = jwtservice;
        this.refreshTokenService = refreshTokenService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest registerRequest) {

        var user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .name(registerRequest.getName())
                .role(UserRole.USER)
                .build();
        User savedUser = userRepository.save(user);

        var accessToken = jwtservice.generateToken(user);
        var refreshToken = refreshTokenService.createResfreshToken(savedUser.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();

    }


    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        var userMail = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()->new UsernameNotFoundException("User not found "));
        var accessToken = jwtservice.generateToken(userMail);
        var refreshToken = refreshTokenService.createResfreshToken(loginRequest.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }


}
