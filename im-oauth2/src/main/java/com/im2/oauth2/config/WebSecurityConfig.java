package com.im2.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by liuyan on 2017/10/9.
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .formLogin().loginPage("/login").permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/health", "/img/*","/customized/*").permitAll()
                .and()
                .requestMatchers()
                .antMatchers("/customized/*","/health", "/img/*", "/login", "/oauth/authorize", "/oauth/confirm_access", "oauth/revoke-token")
                .antMatchers(HttpMethod.OPTIONS)
                .and()
                .authorizeRequests().anyRequest().authenticated()
                .and()
                .csrf().disable(); // 1
    }
}
