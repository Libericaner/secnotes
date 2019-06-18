package com.security.webapp.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private JwtConfig jwtConfig;
    private JwtService jwtService;

    JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig, JwtService jwtService) {

        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.jwtService = jwtService;
        this.setPostOnly(true);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {

            // read request-body
            StringBuilder sb = new StringBuilder();
            request.getReader().lines().forEach(sb::append);

            // parse request-body into user-object
            JwtUserDto credentials = new ObjectMapper().readValue(sb.toString(), JwtUserDto.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getUsername(),
                            credentials.getPassword(),
                            new ArrayList<>()));

        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {

        UsernamePasswordAuthenticationToken u = new UsernamePasswordAuthenticationToken(authResult.getName(), "", authResult.getAuthorities());

        String jwt = jwtService.createToken(u, jwtConfig);

        response.addHeader(jwtConfig.getHttpHeader(), jwtConfig.getTokenPrefix() + jwt);
    }
}
