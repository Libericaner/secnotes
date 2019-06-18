package com.security.webapp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.ArrayList;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtConfig jwtConfig;
    private JwtService jwtService;
    private UserRegistry userRegistry;

    @Autowired
    public WebSecurityConfig(JwtConfig jwtConfig, JwtService jwtService, UserRegistry userRegistry) {

        this.jwtConfig = jwtConfig;
        this.jwtService = jwtService;
        this.userRegistry = userRegistry;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.
                cors().and()
                .authorizeRequests()
                    .antMatchers("/login")
                    .permitAll()
                    .and()
                .authorizeRequests()
                    .anyRequest()
                    .authenticated()
                    .and()
                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtConfig, jwtService))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtConfig, jwtService))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication().
                withUser(userRegistry.getDavid()).
                password(passwordEncoder().encode(userRegistry.getDavidPassword())).roles("ADMIN").
                and().
                withUser(userRegistry.getJoel()).
                password(passwordEncoder().encode(userRegistry.getJoelPassword())).roles("ADMIN");
    }

    @Bean
    @Qualifier("corsConfigurationSource")
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedMethod("POST");
        corsConfiguration.addAllowedMethod("GET");
        corsConfiguration.addAllowedMethod("PUT");
        corsConfiguration.addAllowedMethod("DELETE");
        corsConfiguration.addAllowedHeader("content-type");
        corsConfiguration.addAllowedHeader("authorization");
        corsConfiguration.addExposedHeader(HttpHeaders.AUTHORIZATION);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    private PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
