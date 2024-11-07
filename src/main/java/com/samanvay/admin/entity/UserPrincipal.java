package com.samanvay.admin.entity;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {
    private UserAuth userAuth;

    public UserPrincipal(UserAuth userAuth) {
        this.userAuth = userAuth;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // return Collections.singleton((GrantedAuthority) () -> "USER");
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return this.userAuth.getPassword();
    }

    @Override
    public String getUsername() {
        return this.userAuth.getUsername();
    }
}
