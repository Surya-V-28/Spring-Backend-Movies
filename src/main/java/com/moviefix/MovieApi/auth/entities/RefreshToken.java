package com.moviefix.MovieApi.auth.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.Instant;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tokenId;

    @NotBlank(message = "the filed should not be plank")
    @Column(nullable = false,length = 500)
    private String refreshToken;

    @Column(nullable = false)
    private Instant expirationTime;

    @OneToOne
    private User user;

}
