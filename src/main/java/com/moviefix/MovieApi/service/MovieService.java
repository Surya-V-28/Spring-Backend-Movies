package com.moviefix.MovieApi.service;


import com.moviefix.MovieApi.dto.MoviesDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {

    MoviesDto addMovie(MoviesDto moviesDto, MultipartFile file) throws IOException;
    MoviesDto getMovie(Integer id);
    List<MoviesDto> getAllMovies();
}
