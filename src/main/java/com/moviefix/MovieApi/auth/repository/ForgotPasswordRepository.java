package com.moviefix.MovieApi.auth.repository;

import com.moviefix.MovieApi.auth.entities.ForgotPassword;
import com.moviefix.MovieApi.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {


    @Query("select fp from ForgotPassword fp where fp.otp= ?1 and fp.user = ?2")
    Optional<ForgotPassword> findByOtpAndUser(Integer otp, User user);


}
