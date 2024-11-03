package com.moviefix.MovieApi.auth.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
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
    private final boolean isAccountNonExpired=true;
    private final  boolean isAccountNonLocked =true;
    private final   boolean isCredentialsNonExpired = true;
    private final   boolean isEnabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority(role.name()));
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
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
