package com.example.music.backend.common.security;

import com.example.music.backend.common.security.service.CustomAccessDeniedHandler;
import com.example.music.backend.user.domain.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

@PropertySource("classpath:constants.properties")
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String COOKIES = "JSESSIONID";

    @Value("${maximum.session.count}")
    private int MAXIMUM_SESSIONS_COUNT;

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder encoder;

    public WebSecurityConfig(final UserDetailsService userDetailsService,
                             final PasswordEncoder encoder) {

        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    RestAuthenticationEntryPoint authenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())

                .and()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint())

                .and()
                .cors()
                .disable()
                .csrf()
                .disable()
                .authorizeRequests()

                .antMatchers("/").permitAll()
                .antMatchers("/h2-console/**").permitAll()

                .antMatchers("/admin/**")
                .hasAnyAuthority(Role.ADMIN.name())

                .antMatchers("/login*")
                .permitAll()

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
