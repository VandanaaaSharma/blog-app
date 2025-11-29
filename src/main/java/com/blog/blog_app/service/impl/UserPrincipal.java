package com.blog.blog_app.service.impl;

import com.blog.blog_app.model.Role;
import com.blog.blog_app.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.*;
import java.util.*;

public class UserPrincipal implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Long id, String username, String password,
                         Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal build(User user) {

        Set<GrantedAuthority> auths = new HashSet<>();

        for (Role r : user.getRoles()) {
            auths.add(new SimpleGrantedAuthority(r.getName()));
        }

        return new UserPrincipal(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                auths
        );
    }

    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
    @Override public String getPassword() { return password; }
    @Override public String getUsername() { return username; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
