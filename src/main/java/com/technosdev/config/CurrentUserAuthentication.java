package com.technosdev.config;

import com.technosdev.entities.CurrentUserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.Collection;
import java.util.stream.Collectors;

public class CurrentUserAuthentication implements Authentication {

    private final CurrentUserEntity currentUserEntity;

    public CurrentUserAuthentication(CurrentUserEntity currentUserEntity) {

        if (currentUserEntity == null){
            throw new ExceptionInInitializerError("Não é possível criar currentUserAuthentication sem um currentUser");
        }

        this.currentUserEntity = currentUserEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this
            .currentUserEntity
            .getPermissions()
            .stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.currentUserEntity;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw new IllegalArgumentException("Usuário logado");
    }

    @Override
    public String getName() {
        return this.currentUserEntity.getName();
    }
}
