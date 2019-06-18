package com.security.webapp.configuration;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private JwtConfig jwtConfig;
    private JwtService jwtService;

    JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig, JwtService jwtService) {

        super(authenticationManager);
        this.jwtConfig = jwtConfig;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        // get authorization-header
        String header = request.getHeader(jwtConfig.getHttpHeader());

        if (header == null) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthenticationToken(request);

        if (authentication != null) {

            UsernamePasswordAuthenticationToken trustedUser = new UsernamePasswordAuthenticationToken(
                    authentication.getPrincipal(),
                    authentication.getCredentials(),
                    authentication.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(trustedUser);
        } else {
            SecurityContextHolder.clearContext();
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {

        // get the JWT from the request header and remove the prefix
        String token = request.getHeader(jwtConfig.getHttpHeader());
        token = token.replace(jwtConfig.getTokenPrefix(), "");


        // when parsing fails, the token is not valid and null is returned
        try {
            Claims claims = jwtService.parseToken(jwtConfig.getBase64EncodedSigningKey(), token);

            // only the username is stored in the token
            return new UsernamePasswordAuthenticationToken(claims.getSubject(),
                    null,
                    new ArrayList<>());

        } catch (ExpiredJwtException | MalformedJwtException | SignatureException e) {

            log.error(e.getMessage());
        }

        return null;
    }
}
