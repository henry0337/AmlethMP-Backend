package dev.sh1on.amlethmp.user.service;

import dev.myrlennia237.annotation.spring.EffectiveReadOnlyTransactional;
import dev.myrlennia237.annotation.spring.EffectiveTransactional;
import dev.myrlennia237.component.dto.PagedResponse;
import dev.myrlennia237.template.service.java.AbstractCrudService;
import dev.myrlennia237.utils.CommonUtils;
import dev.sh1on.amlethmp.common.shared.exception.RecordNotFoundException;
import dev.sh1on.amlethmp.user.dto.UserCreateDto;
import dev.sh1on.amlethmp.user.dto.UserDto;
import dev.sh1on.amlethmp.user.dto.UserUpdateDto;
import dev.sh1on.amlethmp.user.mapper.UserMapper;
import dev.sh1on.amlethmp.user.model.User;
import dev.sh1on.amlethmp.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

/**
 * <b>[Domain Service]</b> <br>
 * Lớp xử lý nghiệp vụ cho mô-đun {@link User}.
 *
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Service
@RequiredArgsConstructor
public class UserService extends AbstractCrudService<UserDto, UserCreateDto, UserUpdateDto> {
    private static final Map<String, String> MESSAGES = Map.of(
            "USER_NOT_FOUND", "Cannot find user with id: %s");

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @EffectiveTransactional
    public Mono<Void> deleteById(UUID id) {
        return repository.deleteById(id);
    }

    @EffectiveTransactional
    @SuppressWarnings("java:S4449")
    public Mono<UserDto> insert(UserCreateDto dto) {
        var user = mapper.toUser(dto);
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        CommonUtils.requireNonNull(encodedPassword);
        user.setAccountPassword(encodedPassword);
        return repository.save(user).map(mapper::toUserDto);
    }

    @EffectiveTransactional
    public Mono<UserDto> update(UUID id, UserUpdateDto body) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(userNotFound(id)))
                .flatMap((User user) -> {
                    mapper.updateUser(body, user);
                    if (body.getPassword() != null) {
                        String encodedPassword = passwordEncoder.encode(body.getPassword());
                        CommonUtils.requireNonNull(encodedPassword);
                        user.setAccountPassword(encodedPassword);
                    }
                    return repository.save(user);
                })
                .map(mapper::toUserDto);
    }

    @EffectiveReadOnlyTransactional
    public Mono<UserDto> findById(UUID id) {
        return repository.findById(id).map(mapper::toUserDto);
    }

    @EffectiveReadOnlyTransactional
    public Mono<PagedResponse<UserDto>> findAll(Pageable pageable) {
        return repository.findAllBy(pageable)
                .switchIfEmpty(reactorHelper.emptyFlux())
                .map(mapper::toUserDto)
                .collectList()
                .zipWith(repository.count())
                .map(tuple -> PagedResponse.from(new PageImpl<>(tuple.getT1(), pageable, tuple.getT2())));
    }

    @EffectiveTransactional
    public Mono<Void> disable(UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(userNotFound(id)))
                .flatMap((User user) -> auditorAware.getCurrentAuditor().flatMap((UUID auditor) -> {
                    user.markAsDisabled(auditor, Instant.now());
                    return reactorHelper.discardReturnValue(repository.save(user));
                }));
    }

    @EffectiveTransactional
    public Mono<Void> enable(UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(userNotFound(id)))
                .flatMap((User user) -> {
                    user.restore();
                    return reactorHelper.discardReturnValue(repository.save(user));
                });
    }

    private static RecordNotFoundException userNotFound(UUID id) {
        return new RecordNotFoundException(MESSAGES.get("USER_NOT_FOUND").formatted(id));
    }
}
