package com.moviefix.MovieApi.auth.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
public class AuthFilterService extends OncePerRequestFilter {

    private  final Jwtservice jwtservice;

    private final UserDetailsService userDetailsService;

    public AuthFilterService(Jwtservice jwtservice, UserDetailsService userDetailsService) {
        this.jwtservice = jwtservice;
        this.userDetailsService = userDetailsService;
    }


    @Override
    protected void doFilterInternal(
            @NonNull  HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        String jwt;
        String username;
        if(authHeader==null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }
        jwt = authHeader.substring(7);
        // Extract the username form the token
        username = jwtservice.extractUsername(jwt);

        if(username !=null && SecurityContextHolder.getContext().getAuthentication()==null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(jwtservice.isTokenValid(jwt,userDetails))  {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
