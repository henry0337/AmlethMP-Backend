package dev.sh1on.amlethmp.auth.service;

import java.time.Duration;
import java.time.Instant;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.myrlennia237.annotation.spring.ReadOnlyTransactional;
import dev.myrlennia237.annotation.spring.Transactional;
import dev.myrlennia237.helper.ReactorHelper;
import dev.myrlennia237.template.service.ReactiveService;
import dev.myrlennia237.utils.CommonUtils;
import dev.sh1on.amlethmp.auth.dto.RegisterRequest;
import dev.sh1on.amlethmp.user.dto.UserDto;
import dev.sh1on.amlethmp.user.mapper.UserMapper;
import dev.sh1on.amlethmp.user.model.Role;
import dev.sh1on.amlethmp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * <b>[API Service]</b> <br>
 * Lớp xử lý logic nghiệp vụ liên quan tới xác thực các <b>yêu cầu bảo mật</b>.
 *
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
@Service
@RequiredArgsConstructor
public class AuthService extends ReactiveService implements AuthenticateInstruction {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenBlacklistService tokenBlacklistService;
    private final ReactorHelper reactorHelper;

    @Override
    @ReadOnlyTransactional
    public Mono<String> login(String email, String password) {
        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.defer(() ->
                        Mono.error(new UsernameNotFoundException("Không tìm thấy email hợp lệ cho: " + email))))
                .flatMap(user -> passwordEncoder.matches(password, user.getPassword())
                        ? reactorHelper.only(user)
                        : Mono.error(new BadCredentialsException("Tên người dùng hoặc mật khẩu được cung cấp không hợp lệ!")))
                .map(jwtService::generateToken);
    }

    @Override
    @Transactional
    public Mono<UserDto> register(RegisterRequest dto) {
        var user = userMapper.toUser(dto);
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        CommonUtils.requireNonNull(encodedPassword);
        user.setAccountPassword(encodedPassword);
        user.setRole(Role.USER.toString());
        return userRepository.save(user).map(userMapper::toUserDto);
    }

    @Override
    public Mono<Void> logout() {
        var credentialNotFoundError = Mono.error(new AuthenticationCredentialsNotFoundException(
                        "Không tìm thấy thông tin xác thực cho yêu cầu đăng xuất hiện tại!");

        Mono<Boolean> revoked = ReactiveSecurityContextHolder.getContext()
                .map(securityContext -> {
                    if (securityContext.getAuthentication() == null) {
                        return (Authentication) Mono.empty();
                    }
                    return securityContext.getAuthentication();
                })
                .switchIfEmpty(Mono.defer(() -> Mono.error(new AuthenticationCredentialsNotFoundException(
                        "Không tìm thấy thông tin xác thực cho yêu cầu đăng xuất hiện tại!"))))
                .flatMap(authentication -> authentication.getCredentials() instanceof String token
                        ? revoke(token)
                        : Mono.error(new AuthenticationCredentialsNotFoundException(
                                "Yêu cầu đăng xuất không đi kèm JWT hợp lệ!")));

        return reactorHelper.discardReturnValue(revoked);
    }

    private Mono<Boolean> revoke(String token) {
        var remaining = Duration.between(Instant.now(), jwtService.extractExpiration(token));
        return tokenBlacklistService.blacklistToken(token, remaining);
    }
}
