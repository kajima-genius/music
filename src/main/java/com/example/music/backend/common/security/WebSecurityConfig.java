package com.example.music.backend.common.security;

import com.example.music.backend.common.security.handler.AuthEntryPointJwt;
import com.example.music.backend.common.security.handler.CustomAccessDeniedHandler;
import com.example.music.backend.common.security.jwt.AuthTokenFilter;
import com.example.music.backend.user.domain.Role;
import com.example.music.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@PropertySource("classpath:constants.properties")
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String COOKIES = "JSESSIONID";

    @Value("${maximum.session.count}")
    private int MAXIMUM_SESSIONS_COUNT;

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder encoder;

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    AuthEntryPointJwt authenticationEntryPoint() {
        return new AuthEntryPointJwt();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())

                .and()
                .cors()
                .disable()
                .csrf()
                .disable()
                .authorizeRequests()

                .antMatchers("/h2-console/**", "/auth/**", "/oauth2/**", "/users/registration")
                .permitAll()

                .antMatchers("/videos/**", "/playlists/**", "/users/**")
                .hasAnyAuthority(Role.USER.name())

                .and()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint())

//                .and()
//                .oauth2Login()
//                .authorizationEndpoint()
//                .baseUri("/oauth2/authorize")
//                .authorizationRequestRepository(cookieAuthorizationRequestRepository())
//                .and()
//                .redirectionEndpoint()
//                .baseUri("/oauth2/callback/*")
//                .and()
//                .userInfoEndpoint()
//                .userService(customOAuth2UserService)
//                .and()
//                .successHandler(customAuthenticationSuccessHandler)
//                .failureHandler(customAuthenticationFailureHandler)


//                .oauth2Login()
//                .userInfoEndpoint()
//                .and()
//                .successHandler(new CustomAuthenticationSuccessHandler(userService))

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
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
