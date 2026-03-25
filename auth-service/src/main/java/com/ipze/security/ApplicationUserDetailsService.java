package com.ipze.security;

import com.ipze.exception.UserNotFoundException;
import com.ipze.model.postgres.User;
import com.ipze.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ApplicationUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username)
                .orElseThrow(UserNotFoundException::new);

        return new ApplicationUserDetails(
                user.getUserID().toString(),
                user.getUsername(),
                List.of(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }
}