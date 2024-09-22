package com.moviefix.MovieApi.service;

import com.moviefix.MovieApi.dto.MoviesDto;
import com.moviefix.MovieApi.entities.Movie;
import com.moviefix.MovieApi.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


@Service
public class MovieServiceImpl implements  MovieService{

    private final  MovieRepository movieRepository;
    private  final  FileService fileService;

    @Value("${project.poster}")
    String path;

    @Value("${base.url}")
    String baseUrl;
    public MovieServiceImpl(MovieRepository movieRepository, FileService fileService) {
        this.movieRepository = movieRepository;
        this.fileService = fileService;
    }

    @Override
    public MoviesDto addMovie(MoviesDto moviesDto, MultipartFile file) throws IOException {
        // first lets upload the file so that we  able to add the paths or name of the file in the database'
        String uploadedFileName  = fileService.uploadFile(path,file);
        // 2. update the values of the field
        moviesDto.setPosterUrl(uploadedFileName);
        String fileNameForDataStore = file.getOriginalFilename();
        // 3. add the dats in to the repository
        Movie movies = new Movie(
                null,
                moviesDto.getTitle(),
                moviesDto.getDirector(),
                moviesDto.getStudio(),
                moviesDto.getMovieCast(),
                moviesDto.getReleaseYear(),
                fileNameForDataStore
        );
        // save  the data into repostory
         Movie savedMovies = movieRepository.save(movies);

         // before sending the data we need to generate the response
        String posterUrl = baseUrl + "/file/" + uploadedFileName;

        // Converstion of the Movies to MoviesDto

        MoviesDto moviesDto1 = new MoviesDto(
                savedMovies.getMovieId(),
                savedMovies.getTitle(),
                savedMovies.getDirector(),
                savedMovies.getStudio(),
                savedMovies.getMovieCast(),
                savedMovies.getReleaseYear(),
                savedMovies.getPoster(),
                posterUrl
        );


        return moviesDto1;
    }

    @Override
    public MoviesDto getMovie(Integer id) {
           Movie movie = movieRepository.findById(id).orElseThrow(()-> new RuntimeException("Movies Id not found"));

           // conversation of the movie to movieDto
        String posterUrl = baseUrl + "/file/" + movie.getPoster();
        MoviesDto moviesDto = new MoviesDto(
                movie.getMovieId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getReleaseYear(),
                movie.getPoster(),
                posterUrl

        );
        return moviesDto;
    }

    @Override
    public List<MoviesDto> getAllMovies() {


        List<Movie> movies = movieRepository.findAll();
        List<MoviesDto> moviesDtos = new ArrayList<>();
        for (Movie movie : movies){

            String posterUrl = baseUrl + "/file/" + movie.getPoster();
            MoviesDto moviesDto = new MoviesDto(
                    movie.getMovieId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getPoster(),
                    posterUrl

            );
            moviesDtos.add(moviesDto);
        }
        return moviesDtos;
    }
}
