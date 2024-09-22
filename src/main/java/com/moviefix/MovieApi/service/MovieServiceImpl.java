package com.moviefix.MovieApi.service;

import com.moviefix.MovieApi.dto.MoviesDto;
import com.moviefix.MovieApi.repository.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;



@Service
public class MovieServiceImpl implements  MovieService{

    private final  MovieRepository movieRepository;
    private  final  FileService fileService;

    public MovieServiceImpl(MovieRepository movieRepository, FileService fileService) {
        this.movieRepository = movieRepository;
        this.fileService = fileService;
    }

    @Override
    public MoviesDto addMovie(MoviesDto moviesDto, MultipartFile file) throws IOException {
        // first lets upload the file so that we can able to add the paths or name of the file in the database'
        String path = "";
        String fileName  = fileService.uploadFile(path,file);

        // 2. update the values of the field



        // 3. add the dats in to the repository

        return null;
    }

    @Override
    public MoviesDto getMovie(Integer id) {
        return null;
    }

    @Override
    public List<MoviesDto> getAllMovies() {
        return null;
    }
}
