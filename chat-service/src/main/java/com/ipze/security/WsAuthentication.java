package com.ipze.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class WsAuthentication implements Authentication {

    private final LocalUserDetails user;

    public WsAuthentication(LocalUserDetails user) {
        this.user = user;
    }

    @Override
    public String getName() {
        return user.uuid();
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override public boolean isAuthenticated() { return true; }
    @Override public void setAuthenticated(boolean isAuthenticated) {}
    @Override public Object getDetails() { return null; }
}