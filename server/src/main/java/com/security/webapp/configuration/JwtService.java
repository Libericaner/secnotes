package com.security.webapp.configuration;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;

@Service
public class JwtService {

    Claims parseToken(byte[] base64EncodedSigningKey, String encodedToken)
            throws ExpiredJwtException, MalformedJwtException, SignatureException {

        return Jwts.parser()
                .setSigningKey(base64EncodedSigningKey)
                .parseClaimsJws(encodedToken)
                .getBody();
    }

    String createToken(UsernamePasswordAuthenticationToken user,
                       JwtConfig config) {

        return Jwts.builder()
                .setSubject(user.getName())
                .setHeaderParam("typ", "JWT")
                .setExpiration(new Date(System.currentTimeMillis() + config.getTokenLifetime()))
                .signWith(config.getSignatureAlgorithm(), config.getBase64EncodedSigningKey())
                .compact();
    }
}
