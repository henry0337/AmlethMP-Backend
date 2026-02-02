package dev.sh1on.amlethmp.auth.config;

import dev.sh1on.amlethmp.auth.service.JwtService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import reactor.core.publisher.Mono;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Stella</a>
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {
    JwtService jwtService;
    ReactiveUserDetailsService userDetailsService;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        if (authentication instanceof UsernamePasswordAuthenticationToken token) {
            var tokenStr = (String) token.getCredentials();
            if (tokenStr == null) return Mono.empty();

            String username = jwtService.extractUsername(tokenStr);
            return userDetailsService.findByUsername(username)
                    .filter(user -> jwtService.isTokenValid(tokenStr, user))
                    .map(user -> new UsernamePasswordAuthenticationToken(user, tokenStr, user.getAuthorities()))
                    .switchIfEmpty(Mono.error(new BadCredentialsException("Invalid JWT")))
                    .cast(Authentication.class);
        }
        return Mono.empty();
    }
}
