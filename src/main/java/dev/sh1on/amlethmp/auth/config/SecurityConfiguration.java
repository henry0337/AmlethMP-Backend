package dev.sh1on.amlethmp.auth.config;

import dev.sh1on.amlethmp.common.AmlethMPEndpoint;
import dev.sh1on.amlethmp.auth.service.JwtService;
import dev.myrlennia237.annotation.spring.EnableReactiveSecurityCustomization;
import dev.sh1on.amlethmp.common.shared.constant.AppConstant;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@EnableReactiveSecurityCustomization
@RequiredArgsConstructor
class SecurityConfiguration {
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtService jwtService;
    private final ReactiveUserDetailsService userDetailsService;

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(
                                AmlethMPEndpoint.Auth.BASE + AmlethMPEndpoint.WILDCARD_PATH,
                                AmlethMPEndpoint.User.BASE + "/**",
                                AmlethMPEndpoint.Docs.API_DOCS,
                                AmlethMPEndpoint.Docs.SWAGGER_UI,
                                AmlethMPEndpoint.Docs.SWAGGER_UI_HTML).permitAll()
                        .anyExchange().authenticated()
                )
                .addFilterAt(jwtAuthenticationWebFilter(), SecurityWebFiltersOrder.AUTHENTICATION)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((exchange, _) ->
                                Mono.fromRunnable(() ->
                                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
                )
                .build();
    }

    @Bean
    AuthenticationWebFilter jwtAuthenticationWebFilter() {
        var filter = new AuthenticationWebFilter(reactiveAuthenticationManager());
        filter.setServerAuthenticationConverter(jwtAuthenticationConverter());
        return filter;
    }

    @Bean
    ServerAuthenticationConverter jwtAuthenticationConverter() {
        return (ServerWebExchange exchange) -> {
            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
                return Mono.empty();
            }

            var token = authHeader.substring(BEARER_PREFIX.length());
            String username = jwtService.extractUsername(token);

            return userDetailsService.findByUsername(username)
                    .filter(userDetails -> jwtService.isTokenValid(token, userDetails))
                    .map(userDetails -> new UsernamePasswordAuthenticationToken(
                            userDetails,
                            token,
                            userDetails.getAuthorities()
                    ));
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    ReactiveAuthenticationManager reactiveAuthenticationManager() {
        return Mono::justOrEmpty;
    }
}
