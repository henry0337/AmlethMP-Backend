package dev.sh1on.amlethmp.auth.service;

import dev.sh1on.amlethmp.auth.dto.RegisterRequest;
import dev.sh1on.amlethmp.common.shared.utils.CommonUtils;
import dev.sh1on.amlethmp.common.template.service.AmlethMPService;
import dev.sh1on.amlethmp.user.dto.UserDto;
import dev.sh1on.amlethmp.user.mapper.UserMapper;
import dev.sh1on.amlethmp.user.model.Role;
import dev.sh1on.amlethmp.user.model.User;
import dev.sh1on.amlethmp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService extends AmlethMPService implements JwtAuthenticationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    @Transactional(readOnly = true)
    public Mono<String> login(String email, String password) {
        Mono<User> userMono = reactorUtils.errorIfEmpty(
                userRepository.findByEmail(email),
                () -> new UsernameNotFoundException(i18NService.translateMessage("error.user.not_found")));

        return reactorUtils.ensure(
                userMono,
                user -> passwordEncoder.matches(password, user.getPassword()),
                () -> new BadCredentialsException(i18NService.translateMessage("error.auth.invalid_credentials"))
        ).map(jwtService::generateToken);
    }

    @Override
    public Mono<UserDto> register(RegisterRequest dto) {
        User user = userMapper.toUser(userMapper.toUserDto(dto));
        user.setAccountPassword(CommonUtils.asNonNullable(passwordEncoder.encode(dto.getPassword())));
        // Người dùng tự đăng ký luôn nhận vai trò USER (cột role là NOT NULL)
        user.setRole(Role.USER.name());
        // Map lại từ entity đã lưu để response có id + createdAt/createdBy do DB/Auditing sinh ra
        return userRepository.save(user).map(userMapper::toUserDto);
    }
}

interface JwtAuthenticationService {
    Mono<String> login(String email, String password);
    Mono<UserDto> register(RegisterRequest dto);
}
