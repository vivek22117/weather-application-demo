package com.weather.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.weather.api.util.AppUtility.JWT_TOKEN_VALIDITY;

@Component
@Slf4j
public class AppJwtTokenUtil {

    @Value("$secret.key}")
    private String secret;

    public boolean validateToken(String jwt) {
        final Claims claims = getAllClaimsFromToken(jwt);
        final Date expiration = claims.getExpiration();
        return !expiration.before(new Date());
    }

    private Claims getAllClaimsFromToken(String jwt) {
        log.info("JWT String..." + jwt);
        return Jwts.parserBuilder().setSigningKey(secret.getBytes())
                .build().parseClaimsJws(jwt).getBody();
    }

    public String getUsernameFromToken(String jwt) {
        final Claims claims = getAllClaimsFromToken(jwt);
        return claims.getSubject();
    }

    public String generateTokenWithUsername(String username) {
        return doGenerateToken(username);
    }

    private String doGenerateToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }
}
