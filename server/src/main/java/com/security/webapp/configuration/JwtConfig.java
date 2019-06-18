package com.security.webapp.configuration;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

@Getter
@Configuration
public class JwtConfig {

    @Value("${jwt.http-header}")
    private String httpHeader;
    @Value("${jwt.token-prefix}")
    private String tokenPrefix;
    @Value("${jwt.token-lifetime}")
    private Long tokenLifetime;
    @Value("${jwt.signing-key}")
    private String signingKey;
    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

    byte[] getBase64EncodedSigningKey() {

        return Base64.getEncoder().encode(signingKey.getBytes());
    }
}
