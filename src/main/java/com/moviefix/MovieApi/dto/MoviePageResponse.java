package com.moviefix.MovieApi.dto;


import java.util.List;

public record MoviePageResponse(List<MoviesDto> moviesDtos, Integer pageNumber, Integer pageSize, int totalElements
                                , int totalPage , boolean isLast) {

}
