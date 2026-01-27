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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Sh1on</a>
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements AmlethMPRestService<UserDto, String, UserCreateDto, UserUpdateDto>, Reversible<String> {
    UserRepository repository;
    UserMapper mapper;

    @Override
    public Flux<UserDto> findAll(Pageable pageable, Sort sort) {
        return repository.findAll().map(mapper::toUserDto);
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
        repository.save(user);
        return Mono.just(newUserDto);
    }

    @Override
    @Transactional
    public Mono<UserDto> update(String key, UserUpdateDto dto) {
        return null;
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
                    user.setLastDisabledBy(user.getId());
                    user.setLastDisabledAt(LocalDateTime.now());
                    return repository.save(user);
                })
                .then();
    }
}
