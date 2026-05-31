package com.codespells.pm.common.security;

import com.codespells.pm.common.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

/**
 * JWT uretimi ve dogrulamasi. Access + refresh token destekler.
 */
@Service
public class JwtService {

    private final JwtProperties props;
    private final SecretKey key;

    public JwtService(JwtProperties props) {
        this.props = props;
        this.key = Keys.hmacShaKeyFor(props.secret().getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(String subject, Map<String, Object> claims) {
        return build(subject, claims, props.accessTokenTtlMinutes(), ChronoUnit.MINUTES);
    }

    public String generateRefreshToken(String subject) {
        return build(subject, Map.of("type", "refresh"),
                props.refreshTokenTtlDays() * 24 * 60, ChronoUnit.MINUTES);
    }

    private String build(String subject, Map<String, Object> claims, long amount, ChronoUnit unit) {
        Instant now = Instant.now();
        Instant exp = now.plus(amount, unit);
        return Jwts.builder()
                .subject(subject)
                .claims(claims)
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .signWith(key)
                .compact();
    }

    public Claims parse(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isValid(String token) {
        try {
            parse(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
