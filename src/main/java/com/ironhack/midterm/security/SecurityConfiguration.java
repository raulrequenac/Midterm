package com.ironhack.midterm.security;

import com.ironhack.midterm.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled=true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.httpBasic();

        httpSecurity.authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "/author/add").hasAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.POST,"/post/add").hasAuthority("ROLE_CONTRIBUTOR")
                .mvcMatchers(HttpMethod.PATCH, "/author/{id}/update").hasAuthority("ROLE_CONTRIBUTOR")
                .mvcMatchers(HttpMethod.PATCH,"/post/{id}/update").hasAuthority("ROLE_CONTRIBUTOR")
                .mvcMatchers(HttpMethod.DELETE,"/author/{id}/delete").hasAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.DELETE,"/post/{id}/delete").hasAuthority("ROLE_ADMIN")
                .anyRequest().permitAll();

    }
}
