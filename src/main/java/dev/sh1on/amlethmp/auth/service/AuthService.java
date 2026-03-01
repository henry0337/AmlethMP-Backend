package dev.sh1on.amlethmp.auth.service;

import dev.sh1on.amlethmp.auth.dto.RegisterRequest;
import dev.sh1on.amlethmp.user.dto.UserDto;
import dev.sh1on.amlethmp.user.mapper.UserMapper;
import dev.sh1on.amlethmp.user.model.User;
import dev.sh1on.amlethmp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Patricia</a>
 */
@Service
@RequiredArgsConstructor
public class AuthService implements JwtAuthenticationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    @Transactional(readOnly = true)
    public Mono<String> login(String email, String password) {
        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found")))
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .switchIfEmpty(Mono.error(new BadCredentialsException("Invalid credentials")))
                .map(jwtService::generateToken);
    }

    @Override
    @Transactional
    public Mono<UserDto> register(RegisterRequest dto) {
        UserDto responseData = userMapper.toUserDto(dto);
        User user = userMapper.toUser(responseData);
        user.setAccountPassword(Objects.requireNonNull(passwordEncoder.encode(user.getAccountPassword())));
        userRepository.save(user);
        return Mono.just(responseData);
    }
}