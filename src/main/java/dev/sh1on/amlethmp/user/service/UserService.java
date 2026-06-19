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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;

/**
 * <b>[Domain Service]</b> <br>
 * Lớp xử lý nghiệp vụ cho mô-đun {@link User}.
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserService extends AmlethMPRestService<UserDto, String, UserCreateDto, UserUpdateDto> implements Reversible<String> {
    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;

    @Override
    @Transactional(readOnly = true)
    public Mono<Page<UserDto>> findAll(Pageable pageable) {
        return repository.findAll(pageable)
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
        User user = mapper.toUser(mapper.toUserDto(dto));
        String encodedPassword = CommonUtils.asNonNullable(encoder.encode(user.getPassword()));
        user.setAccountPassword(encodedPassword); // NOSONAR
        return repository.save(user).map(mapper::toUserDto);
    }

    @Override
    public Mono<UserDto> update(String key, UserUpdateDto dto) {
        return repository.findById(key)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found")))
                .flatMap((User user) -> {
                    String encodedPassword = CommonUtils.asNonNullable(encoder.encode(dto.getPassword()));

                    if (dto.getEmail() != null) user.setEmail(dto.getEmail());
                    if (dto.getDisplayName() != null) user.setDisplayName(dto.getDisplayName());
                    if (dto.getRole() != null) user.setRole(dto.getRole().toString());
                    if (dto.getPassword() != null) user.setAccountPassword(encodedPassword);
                    user.setLastUpdatedAt(OffsetDateTime.now());
                    user.setLastUpdatedBy(dto.getUpdatedBy());
                    return repository.save(user);
                })
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
                .flatMap((User user) -> {
                    user.setDisabled(true);
                    user.setLastDisabledAt(OffsetDateTime.now());
                    user.setLastUpdatedAt(OffsetDateTime.now());
                    return repository.save(user);
                })
                .then();
    }
}
