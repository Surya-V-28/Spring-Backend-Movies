package com.moviefix.MovieApi.auth.repository;

import com.moviefix.MovieApi.auth.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {


    Optional<RefreshToken> findByRefreshToken(String refreshToken);

}
