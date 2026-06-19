package dev.sh1on.amlethmp.user.service;

import dev.sh1on.amlethmp.common.shared.exception.UserNotFoundException;
import dev.sh1on.amlethmp.common.shared.utils.CommonUtils;
import dev.sh1on.amlethmp.common.template.service.AmlethMPRestService;
import dev.sh1on.amlethmp.common.template.service.crud.Reversible;
import dev.sh1on.amlethmp.user.dto.UserCreateDto;
import dev.sh1on.amlethmp.user.dto.UserDto;
import dev.sh1on.amlethmp.user.dto.UserUpdateDto;
import dev.sh1on.amlethmp.user.mapper.UserMapper;
import dev.sh1on.amlethmp.user.model.User;
import dev.sh1on.amlethmp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

/**
 * <b>[Domain Service]</b> <br>
 * Lớp xử lý nghiệp vụ cho mô-đun {@link User}.
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserService extends AmlethMPRestService<UserDto, UUID, UserCreateDto, UserUpdateDto> implements Reversible<UUID> {
    private static final String USER_NOT_FOUND = "error.user.not_found";

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;
    private final ReactiveAuditorAware<String> auditorAware;

    @Override
    @Transactional(readOnly = true)
    public Mono<Page<UserDto>> findAll(Pageable pageable) {
        return repository.findAllBy(pageable)
                .switchIfEmpty(Flux.empty())
                .map(mapper::toUserDto)
                .collectList()
                .zipWith(repository.count())
                .map(tuple -> new PageImpl<>(tuple.getT1(), pageable, tuple.getT2()));
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<UserDto> findByKey(UUID key) {
        return repository.findById(key).map(mapper::toUserDto);
    }

    @Override
    @SuppressWarnings("java:S4449")
    public Mono<UserDto> save(UserCreateDto dto) {
        var user = mapper.toUser(dto);
        user.setAccountPassword(CommonUtils.asNonNullable(encoder.encode(dto.getPassword())));
        return repository.save(user).map(mapper::toUserDto);
    }

    @Override
    public Mono<UserDto> update(UUID key, UserUpdateDto dto) {
        return repository.findById(key)
                .switchIfEmpty(Mono.error(new UserNotFoundException(i18NService.translateMessage(USER_NOT_FOUND))))
                .flatMap((User user) -> {
                    if (dto.getEmail() != null) user.setEmail(dto.getEmail());
                    if (dto.getDisplayName() != null) user.setDisplayName(dto.getDisplayName());
                    if (dto.getRole() != null) user.setRole(dto.getRole().toString());
                    if (dto.getPassword() != null) {
                        user.setAccountPassword(CommonUtils.asNonNullable(encoder.encode(dto.getPassword())));
                    }
                    return repository.save(user);
                })
                .map(mapper::toUserDto);
    }

    @Override
    public Mono<Void> deleteById(UUID key) {
        return repository.deleteById(key);
    }

    @Override
    public Mono<Void> disableById(UUID key) {
        return repository.findById(key)
                .switchIfEmpty(Mono.error(new UserNotFoundException(i18NService.translateMessage(USER_NOT_FOUND))))
                .flatMap((User user) -> auditorAware.getCurrentAuditor()
                        .flatMap((String auditor) -> {
                            user.setDisabled(true);
                            user.setLastDisabledAt(Instant.now());
                            user.setLastDisabledBy(auditor);
                            return repository.save(user);
                        }))
                .then();
    }

    @Override
    public Mono<Void> enableById(UUID key) {
        return repository.findById(key)
                .switchIfEmpty(Mono.error(new UserNotFoundException(i18NService.translateMessage(USER_NOT_FOUND))))
                .flatMap((User user) -> {
                    user.setDisabled(false);
                    return repository.save(user);
                })
                .then();
    }
}
