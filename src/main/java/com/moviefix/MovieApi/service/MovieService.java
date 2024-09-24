package com.moviefix.MovieApi.service;


import com.moviefix.MovieApi.dto.MoviesDto;
import com.moviefix.MovieApi.entities.Movie;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {

    MoviesDto addMovie(MoviesDto moviesDto, MultipartFile file) throws IOException;
    MoviesDto getMovie(Integer id);
    List<MoviesDto> getAllMovies();
    MoviesDto updateMovie(Integer movieId, MoviesDto moviesDto, MultipartFile file) throws IOException;
    String deleteMovie(Integer id) throws IOException;
}
