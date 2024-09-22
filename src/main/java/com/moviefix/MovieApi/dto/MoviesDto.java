package com.moviefix.MovieApi.dto;


import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoviesDto {

    private  Integer movieId;


    @NotBlank(message = "please provide the Title")
    private String title;


    @NotBlank(message = "please provide the director")
    private  String director;


    @NotBlank(message = "please provide the Studio")
    private  String studio;


    private Set<String> movieCast;


    private Integer releaseYear;

    @NotBlank(message = "please provide the poster")
    private String poster;

    @NotBlank(message = "please provide the poster urls")
    private String posterUrl;

}
