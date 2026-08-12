package dev.sh1on.amlethmp.auth.service;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import dev.sh1on.amlethmp.user.model.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Service
@Slf4j
public class JwtService {
    private static final Duration EXPIRATION_TIME = Duration.ofHours(10);

    private final SecretKey key;

    public JwtService(@Value("${jwt.secret}") String secret) {
        if (secret.isBlank()) {
            throw new IllegalStateException("Secret JWT chưa được thiết lập!");
        }
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserDetails userDetails) {
        String username = userDetails.getUsername();
        String role = !userDetails.getAuthorities().isEmpty()
                ? userDetails.getAuthorities().iterator().next().getAuthority()
                : null;

        var now = Instant.now();
        var expiry = now.plus(EXPIRATION_TIME);

        return Jwts.builder()
                .subject(username)
                .claim("role", role != null ? role : Role.USER.toString())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Lấy ra thời điểm hết hạn được ghi trong {@code token} được chỉ định.
     *
     * @param token Token cần lấy thời điểm hết hạn
     * @return Thời điểm token đó hết hiệu lực.
     * @throws io.jsonwebtoken.JwtException Nếu token không hợp lệ hoặc không thể phân giải.
     */
    public Instant extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration).toInstant();
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
