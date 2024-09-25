package com.moviefix.MovieApi.auth.repository;

import com.moviefix.MovieApi.auth.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {

}
