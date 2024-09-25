package com.moviefix.MovieApi.auth.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer userId;
    @NotBlank(message = " this filed cant be blank or empty")
    private String name;
    @NotBlank(message = " this filed cant be blank or empty")
    @Column(unique = true)
    private  String username;
    @NotBlank(message = " this filed cant be blank or empty")
    @Column(unique = true)
    @Email(message = "please enter the email in password")
    private String email;
    @NotBlank(message = " this filed cant be blank or empty")
    @Size(min = 6,message = "the password should have min 6 words")
    private  String password;

    @OneToOne(mappedBy = "user")
    private RefreshToken  refreshToken;
    @Enumerated(EnumType.STRING)
    private  UserRole role;
    private boolean isAccountNonExpired=true;
    private boolean isAccountNonLocked =true;
    private  boolean isCredentialsNonExpired = true;
    private  boolean isEnabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
