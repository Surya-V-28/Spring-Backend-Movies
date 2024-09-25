package com.moviefix.MovieApi.service;

import com.moviefix.MovieApi.dto.MoviePageResponse;
import com.moviefix.MovieApi.dto.MoviesDto;
import com.moviefix.MovieApi.entities.Movie;
import com.moviefix.MovieApi.exception.FileExistsException;
import com.moviefix.MovieApi.exception.MovieNotExistException;
import com.moviefix.MovieApi.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

       if(Files.exists(Paths.get(path + File.separator + file.getOriginalFilename())))  throw new FileExistsException("please enter the new name of the file");
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
           Movie movie = movieRepository.findById(id).orElseThrow(()-> new MovieNotExistException("Movies Id not found" + id));
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
        List<MoviesDto> moviesDtos = getMoviesDtos(movieRepository.findAll());
        return moviesDtos;
    }

    @Override
    public MoviesDto updateMovie(Integer movieId, MoviesDto moviesDto, MultipartFile file) throws IOException {
        // get the current movie entity with the movieDto Updated
        Movie currMovie = movieRepository.findById(movieId).orElseThrow(()-> new MovieNotExistException("Don't have the movie with id" + movieId));
        // get the file name
        String fileName = currMovie.getPoster();
        if(file !=null) {
            Files.deleteIfExists(Paths.get(path + File.separator + fileName));
            fileName = fileService.uploadFile(path,file);
        }
        moviesDto.setPoster(fileName);
        Movie mv = new Movie(
                moviesDto.getMovieId(),
                moviesDto.getTitle(),
                moviesDto.getDirector(),
                moviesDto.getStudio(),
                moviesDto.getMovieCast(),
                moviesDto.getReleaseYear(),
                moviesDto.getPoster()
        );

        Movie updatedMovie = movieRepository.save(mv);


        // generate the poster Url

        String posterUrl = baseUrl + "/file/" + fileName;

        MoviesDto  response = new MoviesDto(
                mv.getMovieId(),
                mv.getTitle(),
                mv.getDirector(),
                mv.getStudio(),
                mv.getMovieCast(),
                mv.getReleaseYear(),
                mv.getPoster(),
                posterUrl

        );

        return response;
    }

    @Override
    public String deleteMovie(Integer id) throws IOException {
        Movie currMovie = movieRepository.findById(id).orElseThrow(()-> new MovieNotExistException("Don't have the movie with id" + id));
        String fileName = currMovie.getPoster();
        Files.deleteIfExists(Paths.get(path + File.separator + fileName));
        movieRepository.delete(currMovie);
        return "Movie deleted with id " + id;

    }

    @Override
    public MoviePageResponse getAllMoviesWithPagination(Integer pageNumber, Integer pageSize) {
        Pageable pageable =  PageRequest.of(pageNumber,pageSize);
          Page<Movie>  moviesPage = movieRepository.findAll(pageable);
        List<MoviesDto> moviesDtos = getMoviesDtos(moviesPage.getContent());
        return new MoviePageResponse(moviesDtos,pageNumber,pageSize, (int) moviesPage.getTotalElements(),moviesPage.getTotalPages(),moviesPage.isLast());
    }

    @Override
    public MoviePageResponse getAllMoviesWithPaginationAndSorting(Integer pageNumber, Integer pageSize, String sortby, String dir) {
        Sort sort = dir.equalsIgnoreCase("asc") ? Sort.by(sortby).ascending() : Sort.by(sortby).descending();
        Pageable pageable =  PageRequest.of(pageNumber,pageSize,sort);
        Page<Movie>  moviesPage = movieRepository.findAll(pageable);
        List<MoviesDto> moviesDtos = getMoviesDtos(moviesPage.getContent());
        return new MoviePageResponse(moviesDtos,pageNumber,pageSize, (int) moviesPage.getTotalElements(),moviesPage.getTotalPages(),moviesPage.isLast());
    }

    private List<MoviesDto> getMoviesDtos(List<Movie> moviesPage) {
        List<Movie> movies = moviesPage;
        List<MoviesDto> moviesDtos = new ArrayList<>();
        for (Movie movie : movies) {

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
