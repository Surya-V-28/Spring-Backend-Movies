package com.moviefix.MovieApi.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer movieId;
    @Column(nullable = false,length = 200)
    @NotBlank(message = "please provide the Title")
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "please provide the director")
    private  String director;

    @Column(nullable = false)
    @NotBlank(message = "please provide the Studio")
    private  String studio;

    @ElementCollection
    @CollectionTable(name = "movie_cast")
    private Set<String> movieCast;

    @Column(nullable = false)
    private Integer releaseYear;
    @Column(nullable = false)
    @NotBlank(message = "please provide the poster")
    private String poster;
}
