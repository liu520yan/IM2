package com.im2.oauth2.config;

import com.im2.common.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by liuyan on 2018/8/23.
 */
public class IMUserDetails implements UserDetails {

    private UserEntity userEntity;

    public IMUserDetails(UserEntity entity) {
        this.userEntity = entity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> ls = new ArrayList<GrantedAuthority>();
        ls.add(new SimpleGrantedAuthority("a"));
        ls.add(new SimpleGrantedAuthority("b"));
        ls.add(new SimpleGrantedAuthority("c"));
        ls.add(new SimpleGrantedAuthority("d"));
        return ls;
    }

    @Override
    public String getPassword() {
        return userEntity == null ? null : userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity == null ? null : userEntity.getUsername();
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
