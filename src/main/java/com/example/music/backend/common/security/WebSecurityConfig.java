package com.example.music.backend.common.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String COOKIES = "JSESSIONID";
    private static final int MAXIMUM_SESSIONS_COUNT = 1;


    private final UserDetailsService userDetailsService;
    private final PasswordEncoder encoder;

    public WebSecurityConfig(final UserDetailsService userDetailsService,
                             final PasswordEncoder encoder) {

        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .httpBasic()

                .and()
                .cors()
                .disable()
                .csrf()
                .disable()
                .authorizeRequests()

                .and()
                .authorizeRequests()

                .antMatchers("/").permitAll()
                .antMatchers("/h2-console/**").permitAll()

                .and()
                .httpBasic()

                .and()
                .logout()
                .invalidateHttpSession(true)
                .deleteCookies(COOKIES)
                .permitAll()

                .and()
                .sessionManagement()
                .sessionFixation().none()
                .maximumSessions(MAXIMUM_SESSIONS_COUNT);

        http.headers().frameOptions().disable();
    }

}
