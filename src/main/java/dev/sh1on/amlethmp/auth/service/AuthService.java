package dev.sh1on.amlethmp.auth.service;

import dev.myrlennia237.annotation.spring.EffectiveReadOnlyTransactional;
import dev.myrlennia237.annotation.spring.EffectiveTransactional;
import dev.myrlennia237.helper.ReactorHelper;
import dev.myrlennia237.template.service.BaseReactiveService;
import dev.myrlennia237.util.CommonUtils;
import dev.sh1on.amlethmp.auth.dto.RegisterRequest;
import dev.sh1on.amlethmp.user.dto.UserDto;
import dev.sh1on.amlethmp.user.mapper.UserMapper;
import dev.sh1on.amlethmp.user.model.Role;
import dev.sh1on.amlethmp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Service
@RequiredArgsConstructor
public class AuthService extends BaseReactiveService implements JwtAuthenticationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ReactorHelper reactorHelper;

    @EffectiveReadOnlyTransactional
    public Mono<String> login(String email, String password) {
        return userRepository.findByEmail(email)
                .switchIfEmpty(Mono.defer(() ->
                        Mono.error(new UsernameNotFoundException("Không tìm thấy email hợp lệ cho: " + email))))
                .flatMap(user -> passwordEncoder.matches(password, user.getPassword())
                        ? reactorHelper.only(user)
                        : Mono.error(new BadCredentialsException("Tên người dùng hoặc mật khẩu được cung cấp không hợp lệ!")))
                .map(jwtService::generateToken);
    }

    @EffectiveTransactional
    @SuppressWarnings("java:S4449")
    public Mono<UserDto> register(RegisterRequest dto) {
        var user = userMapper.toUser(dto);
        user.setAccountPassword(CommonUtils.requireNonNull(passwordEncoder.encode(dto.getPassword())));
        user.setRole(Role.USER.toString());
        return userRepository.save(user).map(userMapper::toUserDto);
    }
}

interface JwtAuthenticationService {
    Mono<String> login(String email, String password);
    Mono<UserDto> register(RegisterRequest dto);
}
