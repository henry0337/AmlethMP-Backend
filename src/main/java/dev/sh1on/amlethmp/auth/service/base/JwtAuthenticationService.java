package dev.sh1on.amlethmp.auth.service.base;

import dev.sh1on.amlethmp.auth.dto.RegisterRequest;
import dev.sh1on.amlethmp.user.dto.UserDto;
import reactor.core.publisher.Mono;

public interface JwtAuthenticationService {
    Mono<String> login(String email, String password);
    Mono<UserDto> register(RegisterRequest dto);
}
