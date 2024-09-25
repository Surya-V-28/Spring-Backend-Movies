package com.moviefix.MovieApi.auth.repository;

import com.moviefix.MovieApi.auth.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {


}
