package dev.sh1on.amlethmp.auth.service;

import dev.sh1on.amlethmp.common.shared.utils.CommonUtils;
import dev.sh1on.amlethmp.common.template.service.AmlethMPService;
import dev.sh1on.amlethmp.user.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.function.Function;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Service
@Slf4j
public class JwtService extends AmlethMPService {
    private static final Duration EXPIRATION_TIME = Duration.ofHours(10);

    private final SecretKey key;

    public JwtService(@Value("${jwt.secret}") String secret) {
        if (secret.isBlank()) {
            throw new IllegalStateException("JWT secret not configured");
        }
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    String generateToken(UserDetails userDetails) {
        String username = userDetails.getUsername();
        String role = userDetails.getAuthorities().isEmpty()
                ? null
                : userDetails.getAuthorities().iterator().next().getAuthority();

        var now = Instant.now();

        return Jwts.builder()
                .subject(username)
                .claim("role", CommonUtils.asNonNullable(role, Role.USER.toString()))
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(EXPIRATION_TIME)))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            String username = extractUsername(token);
            return username.equals(userDetails.getUsername());
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("Token không hợp lệ: {}", e.getMessage(), e);
            return false;
        }
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        var claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
