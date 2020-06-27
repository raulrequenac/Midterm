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
                .mvcMatchers(HttpMethod.GET, "/accounts/{id}").hasAuthority("ROLE_ACCOUNT_HOLDER")
                .mvcMatchers(HttpMethod.GET, "/accounts/{id}/balance").hasAnyAuthority("ROLE_ACCOUNT_HOLDER", "ROLE_ADMIN")
                .mvcMatchers(HttpMethod.POST, "/accounts/{id}/credit").hasAnyAuthority("ROLE_ACCOUNT_HOLDER", "ROLE_THIRD_PARTY")
                .mvcMatchers(HttpMethod.POST, "/accounts/{id}/debit").hasAnyAuthority("ROLE_ACCOUNT_HOLDER", "ROLE_THIRD_PARTY")
                .mvcMatchers(HttpMethod.POST, "/accounts/{id}/unfreeze").hasAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.POST, "/accounts/{id}/transfer").hasAuthority("ROLE_ACCOUNT_HOLDER")
                .mvcMatchers(HttpMethod.POST, "/accounts/checkings").hasAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.POST, "/accounts/credit-cards").hasAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.POST, "/accounts/savings").hasAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.POST, "/users/account-holders").hasAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.POST, "/users/third-parties").hasAuthority("ROLE_ADMIN")
                .mvcMatchers(HttpMethod.POST, "/users/admins").hasAuthority("ROLE_ADMIN")
                .anyRequest().permitAll();

    }
}
