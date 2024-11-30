package edu.bowiestateuni.groupproj.foodpantry.services.impl;

import edu.bowiestateuni.groupproj.foodpantry.services.security.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;


@Service
@Slf4j
public class JWTServiceImpl implements JWTService {
    @Value("${security.jwt.token.issuer}")
    private String issuer;
    @Value("${security.secretkey}")
    private String secretKey;

    @Override
    public String expiringToken(final Map<String, String> attributes, final int minutes) {
        return newToken(attributes, secretKey, minutes);
    }

    @Override
    public Map<String, String> verify(final String token) {
        JwtParser parser = Jwts
                .parser()
                .requireIssuer(issuer)
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build();
        return parseClaims(() -> parser.parseSignedClaims(token).getPayload());
    }

    private String newToken(final Map<String, String> attributes, final String secretKey, final int expiresInMin) {
        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime expiresAt = now.plusMinutes(expiresInMin);
        return Jwts
                .builder()
                .claims(attributes)
                .issuer(issuer)
                .issuedAt(fromLocalDateTimeToDate(now))
                .expiration(fromLocalDateTimeToDate(expiresAt))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    private static Map<String, String> parseClaims(final Supplier<Claims> toClaims) {
        Map<String, String> stringMap = new HashMap<>();
        try {
            final Claims claims = toClaims.get();
            for (final Map.Entry<String, Object> e : claims.entrySet()) {
                stringMap.put(e.getKey(), String.valueOf(e.getValue()));
            }
            return stringMap;
        } catch (final IllegalArgumentException | JwtException e) {
            log.error(e.getLocalizedMessage());
            return stringMap;
        }
    }

    private Date fromLocalDateTimeToDate(final LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
