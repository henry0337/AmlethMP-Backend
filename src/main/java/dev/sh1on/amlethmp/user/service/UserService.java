package dev.sh1on.amlethmp.user.service;

import dev.sh1on.amlethmp.common.exception.UserNotFoundException;
import dev.sh1on.amlethmp.common.template.service.AmlethMPRestService;
import dev.sh1on.amlethmp.common.template.service.crud.Reversible;
import dev.sh1on.amlethmp.common.utils.MessageUtils;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Lớp xử lý <b>logic nghiệp vụ</b> cho mô-đun {@link User}.
 * @author <a href="https://github.com/AdorableDandelion25">Patricia</a>
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements AmlethMPRestService<UserDto, String, UserCreateDto, UserUpdateDto>, Reversible<String> {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;
    private final MessageUtils messageUtils;

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
    public Mono<UserDto> findByKey(String key) {
        return repository.findById(key).map(mapper::toUserDto);
    }

    @Override
    public Mono<UserDto> save(UserCreateDto dto) {
        UserDto newUserDto = mapper.toUserDto(dto);
        User user = mapper.toUser(newUserDto);
        String encodedPassword = encoder.encode(user.getPassword());
        user.setAccountPassword(
                Objects.requireNonNull(
                        encodedPassword,
                        messageUtils.obtainLocalizedStaticMessage("message.password")));
        repository.save(user);
        return Mono.just(newUserDto);
    }

    @Override
    public Mono<UserDto> update(String key, UserUpdateDto dto) {
        return repository.findById(key)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found")))
                .map(user -> {
                    if (dto.getEmail() != null) user.setEmail(dto.getEmail());
                    if (dto.getDisplayName() != null) user.setDisplayName(dto.getDisplayName());
                    if (dto.getRole() != null) user.setRole(dto.getRole().toString());
                    user.setAccountPassword(Objects.requireNonNull(encoder.encode(dto.getPassword())));
                    user.setAccountExpired(false);
                    user.setAccountLocked(false);
                    user.setCredentialsExpired(false);
                    user.setLastUpdatedAt(dto.getUpdatedAt().toString());
                    user.setLastUpdatedBy(dto.getUpdatedBy());

                    return user;
                })
                .flatMap(repository::save)
                .map(mapper::toUserDto);
    }

    @Override
    public Mono<Void> deleteById(String key) {
        return repository.deleteById(key);
    }

    @Override
    public Mono<Void> disableById(String key) {
        return repository.findById(key)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found")))
                .flatMap(user -> {
                    user.setDisabled(true);
                    user.setLastDisabledAt(LocalDateTime.now().toString());
                    user.setLastUpdatedAt(LocalDateTime.now().toString());
                    return repository.save(user);
                })
                .then();
    }
}
