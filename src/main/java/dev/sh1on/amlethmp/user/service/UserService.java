package dev.sh1on.amlethmp.user.service;

import dev.myrlennia237.component.dto.PagedResponse;
import dev.myrlennia237.template.service.java.AbstractCrudService;
import dev.myrlennia237.util.CommonUtils;
import dev.sh1on.amlethmp.common.shared.exception.UserNotFoundException;
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
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

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
    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Mono<Void> deleteById(UUID id) {
        return repository.deleteById(id);
    }

    @Transactional
    @SuppressWarnings("java:S4449")
    public Mono<UserDto> insert(UserCreateDto dto) {
        var user = mapper.toUser(dto);
        String encodedPassword = CommonUtils.requireNonNull(passwordEncoder.encode(dto.getPassword()));
        user.setAccountPassword(encodedPassword);
        return repository.save(user).map(mapper::toUserDto);
    }

    @Transactional
    public Mono<UserDto> update(UUID id, UserUpdateDto body) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException("Cannot find user with id: " + id)))
                .flatMap((User user) -> {
                    mapper.updateUser(body, user);
                    if (body.getPassword() != null) {
                        user.setAccountPassword(CommonUtils.requireNonNull(passwordEncoder.encode(body.getPassword())));
                    }
                    return repository.save(user);
                })
                .map(mapper::toUserDto);
    }

    @Transactional(readOnly = true)
    public Mono<UserDto> findById(UUID id) {
        return repository.findById(id).map(mapper::toUserDto);
    }

    @Transactional(readOnly = true)
    public Mono<PagedResponse<UserDto>> findAll(Pageable pageable) {
        return repository.findAllBy(pageable)
                .switchIfEmpty(reactorHelper.emptyFlux())
                .map(mapper::toUserDto)
                .collectList()
                .zipWith(repository.count())
                .map(tuple -> PagedResponse.from(new PageImpl<>(tuple.getT1(), pageable, tuple.getT2())));
    }

    @Transactional
    public Mono<Void> disable(UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException("Cannot find user with id: " + id)))
                .flatMap((User user) -> auditorAware.getCurrentAuditor().flatMap((UUID auditor) -> {
                    user.markAsDeleted(auditor);
                    return reactorHelper.ignoreReturnValue(repository.save(user));
                }));
    }

    @Transactional
    public Mono<Void> enable(UUID id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException("Cannot find user with id: " + id)))
                .flatMap((User user) -> {
                    user.restore();
                    return reactorHelper.ignoreReturnValue(repository.save(user));
                });
    }
}
