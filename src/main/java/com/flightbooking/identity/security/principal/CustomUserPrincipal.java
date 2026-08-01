package com.flightbooking.identity.security.principal;

import com.flightbooking.identity.user.entity.User;
import com.flightbooking.identity.user.enums.UserStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserPrincipal implements UserDetails { //Security representation of User

    private final User user;

    public CustomUserPrincipal(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }


    @Override
    public boolean isAccountNonLocked() {
        return user.getUserStatus() != UserStatus.BLOCKED;
    }


    @Override
    public boolean isEnabled() {
        return user.getUserStatus() != UserStatus.DEACTIVATED;
    }

    public Long getUserId() {
        return user.getUserId();
    }
}