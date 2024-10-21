package com.moviefix.MovieApi.auth.service;


import com.moviefix.MovieApi.auth.entities.RefreshToken;
import com.moviefix.MovieApi.auth.entities.User;
import com.moviefix.MovieApi.auth.repository.RefreshTokenRepository;
import com.moviefix.MovieApi.auth.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken createResfreshToken(String username){
        User user =  userRepository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User not found"));
         RefreshToken refreshToken = user.getRefreshToken();
         if(refreshToken==null)  {
             long timeXpire = 50*1000;
             refreshToken = RefreshToken.builder().
                     refreshToken(UUID.randomUUID().toString())
                     .expirationTime(Instant.now().plusMillis(timeXpire))
                     .user(user).
                     build();
             refreshTokenRepository.save(refreshToken);
         }
         return refreshToken;
    }


    public  RefreshToken verifyRefreshToken(String refreshToken)  {
        RefreshToken token = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()-> new RuntimeException("Token not found in database"));
        if(token.getExpirationTime().compareTo(Instant.now())<0)  {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("User need to create the new token");
        }
        return  token;
    }
}
