package com.moviefix.MovieApi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviefix.MovieApi.dto.MoviePageResponse;
import com.moviefix.MovieApi.dto.MoviesDto;
import com.moviefix.MovieApi.service.MovieService;
import com.moviefix.MovieApi.utils.AppConstants;
import jakarta.persistence.Index;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PostMapping("/update/{movieId}")
    public  ResponseEntity<MoviesDto> updateMovies(@PathVariable Integer movieId, @RequestPart String moviesDto, @RequestPart MultipartFile file) throws IOException {
        if(file.isEmpty()) file =null;
        MoviesDto dto = convertStrtoMoviesDto(moviesDto);
        MoviesDto moviesDto1 = movieService.updateMovie(movieId,dto
                ,file);
        return ResponseEntity.ok(moviesDto1);
    }
    @PostMapping("/delete/{movieId}")
    public  ResponseEntity<String> deleteMovie(@PathVariable  Integer movieId) throws IOException {
        return ResponseEntity.ok(movieService.deleteMovie(movieId));

    }

    @GetMapping("/getAllMoviePage")
    public  ResponseEntity<MoviePageResponse> getAllMoviePage(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize){
        return ResponseEntity.ok(movieService.getAllMoviesWithPagination(pageNumber, pageSize));
    }

    @GetMapping("/getAllMoviePageSort")
    public  ResponseEntity<MoviePageResponse> getAllMoviePageSort(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.SORT_BY,required = false) String sortby,
            @RequestParam(defaultValue = AppConstants.SORT_DIR,required = false) String dir
            ){
        return ResponseEntity.ok(movieService.getAllMoviesWithPaginationAndSorting(pageNumber, pageSize,sortby,dir));
    }




    private  MoviesDto convertStrtoMoviesDto(String moviestoString ) throws JsonProcessingException {
        ObjectMapper obj = new ObjectMapper();
        MoviesDto moviesDto = obj.readValue(moviestoString,MoviesDto.class);
        return moviesDto;
    }
}
