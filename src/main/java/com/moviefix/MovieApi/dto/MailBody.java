package com.moviefix.MovieApi.dto;


import lombok.Builder;

@Builder
public record MailBody(String to, String Subject, String message) {
}
