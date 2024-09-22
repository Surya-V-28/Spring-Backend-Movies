package com.moviefix.MovieApi.repository;

import com.moviefix.MovieApi.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Integer> {

}
