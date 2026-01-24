package dev.sh1on.amlethmp.user.service;

import dev.sh1on.amlethmp.common.shared.service.BaseApiService;
import dev.sh1on.amlethmp.user.model.User;
import dev.sh1on.amlethmp.user.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Sh1on</a>
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements BaseApiService<User, String> {
    UserRepository repository;

    public Flux<User> getAll() {
        return repository.findAll();
    }

    public Mono<User> getById(String id) {
        return repository.findById(id);
    }

    @Transactional
    public Mono<User> create(User user) {
        return repository.save(user);
    }

    @Transactional
    public Mono<User> update(String id, User user) {
        return null;
    }

    @Transactional
    public Mono<Void> delete(String id) {
        return repository.deleteById(id);
    }
}
