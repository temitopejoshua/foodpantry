package edu.bowiestateuni.groupproj.foodpantry.services.security;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
@Slf4j
@Data
public class AuthenticatedUser implements UserDetails {
    private Long userId;
    private String email;
    private String password;
    private String name;
    private boolean active = true;
    private Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
    public void addAuthority(String name) {
        authorities.add(new SimpleGrantedAuthority(name));
    }

}
