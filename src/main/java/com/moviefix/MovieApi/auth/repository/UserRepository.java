package com.moviefix.MovieApi.auth.repository;

import com.moviefix.MovieApi.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String username);
}
