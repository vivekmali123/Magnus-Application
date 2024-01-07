package com.jala.ems.config;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.jala.ems.model.UserDtls;

public class CustomUserDetails implements UserDetails {

    private UserDtls user;

    public CustomUserDetails(UserDtls user) {
        super();
        this.user = user;
    }

    // Returns the authorities granted to the user
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(user.getRole());
        return Arrays.asList(simpleGrantedAuthority);
    }

    // Returns the password of the user
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // Returns the email address of the user
    @Override
    public String getUsername() {
        return user.getEmail();
    }

    // Indicates whether the user's account has expired
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Indicates whether the user is locked or not
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Indicates whether the user's credentials (password) has expired
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Indicates whether the user is enabled or disabled
    @Override
    public boolean isEnabled() {
        return true;
    }
}
