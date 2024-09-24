package com.moviefix.MovieApi.exception;

public class MovieNotExistException extends  RuntimeException{
    public  MovieNotExistException(String message){
        super(message);
    }
}
