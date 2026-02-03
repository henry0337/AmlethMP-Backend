package dev.sh1on.amlethmp.user.service;

import dev.sh1on.amlethmp.common.template.service.AmlethMPRestService;
import dev.sh1on.amlethmp.common.template.service.Reversible;
import dev.sh1on.amlethmp.user.dto.UserCreateDto;
import dev.sh1on.amlethmp.user.dto.UserDto;
import dev.sh1on.amlethmp.user.dto.UserUpdateDto;
import dev.sh1on.amlethmp.user.mapper.UserMapper;
import dev.sh1on.amlethmp.user.model.User;
import dev.sh1on.amlethmp.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
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
 * @author <a href="https://github.com/AdorableDandelion25">Stella</a>
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements AmlethMPRestService<UserDto, String, UserCreateDto, UserUpdateDto>, Reversible<String> {
    UserRepository repository;
    UserMapper mapper;
    PasswordEncoder encoder;

    @Override
    public Mono<Page<UserDto>> findAll(Pageable pageable) {
        return repository.findAllBy(pageable)
                .switchIfEmpty(Flux.empty())
                .map(mapper::toUserDto)
                .collectList()
                .zipWith(repository.count())
                .map(tuple -> new PageImpl<>(tuple.getT1(), pageable, tuple.getT2()));
    }

    @Override
    public Mono<UserDto> findByKey(String key) {
        return repository.findById(key).map(mapper::toUserDto);
    }

    @Override
    @Transactional
    public Mono<UserDto> save(UserCreateDto dto) {
        UserDto newUserDto = mapper.toUserDto(dto);
        User user = mapper.toUser(newUserDto);
        String encodedPassword = encoder.encode(user.getPassword());
        user.setAccountPassword(Objects.requireNonNull(encodedPassword));
        repository.save(user);
        return Mono.just(newUserDto);
    }

    @Override
    @Transactional
    public Mono<UserDto> update(String key, UserUpdateDto dto) {
        return repository.findById(key)
                .switchIfEmpty(Mono.error(new Exception("User not found")))
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
    @Transactional
    public Mono<Void> deleteById(String key) {
        return repository.deleteById(key);
    }

    @Override
    @Transactional
    public Mono<Void> disableById(String key) {
        return repository.findById(key)
                .switchIfEmpty(Mono.error(new Exception("User not found")))
                .flatMap(user -> {
                    user.setDisabled(true);
                    user.setLastDisabledAt(LocalDateTime.now().toString());
                    user.setLastUpdatedAt(LocalDateTime.now().toString());
                    return repository.save(user);
                })
                .then();
    }
}
