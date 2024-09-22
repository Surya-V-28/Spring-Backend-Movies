package com.moviefix.MovieApi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviefix.MovieApi.dto.MoviesDto;
import com.moviefix.MovieApi.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {

    private  final MovieService movieService;


    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/add")
    public ResponseEntity<MoviesDto> addNewMovie(@RequestPart MultipartFile file, @RequestPart String moviestoString ) throws IOException {
        MoviesDto dto = convertStrtoMoviesDto(moviestoString);
        return new ResponseEntity<>(movieService.addMovie(dto,file), HttpStatus.CREATED);

    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MoviesDto> getSingleMovieHandler(@PathVariable Integer movieId){
        return ResponseEntity.ok(movieService.getMovie(movieId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<MoviesDto>> getAllMovies(){
        return ResponseEntity.ok(movieService.getAllMovies());
    }
    private  MoviesDto convertStrtoMoviesDto(String moviestoString ) throws JsonProcessingException {
        ObjectMapper obj = new ObjectMapper();
        MoviesDto moviesDto = obj.readValue(moviestoString,MoviesDto.class);
        return moviesDto;
    }
}
