package com.example.music.backend.common.security;

import com.example.music.backend.common.security.handler.CustomAccessDeniedHandler;
import com.example.music.backend.user.domain.Role;
import com.example.music.backend.user.service.UserService;
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
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.access.AccessDeniedHandler;


@PropertySource("classpath:constants.properties")
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String COOKIES = "JSESSIONID";
    private final UserService userService;

    @Value("${maximum.session.count}")
    private int MAXIMUM_SESSIONS_COUNT;

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder encoder;

    public WebSecurityConfig(UserService userService, UserDetailsService userDetailsService, PasswordEncoder encoder) {
        this.userService = userService;
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
                .antMatchers("/h2-console/**", "/login**", "/user/registration", "/oauth2**").permitAll()

                .antMatchers("/admin/**")
                .hasAnyAuthority(Role.ADMIN.name())

                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/perform-login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/home")
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .and()
                .successHandler(new CustomAuthenticationSuccessHandler(userService))

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
