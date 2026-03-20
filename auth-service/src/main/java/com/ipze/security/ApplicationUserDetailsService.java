package com.ipze.security;

import com.ipze.exception.UserNotFoundException;
import com.ipze.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class ApplicationUserDetailsService implements UserDetailsService {

    private final AuthService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new ApplicationUserDetails(
                userService.getUserByUsername(username)
                .orElseThrow(UserNotFoundException::new)
        );
    }
}