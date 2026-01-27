package dev.sh1on.amlethmp.user.repository;

import dev.sh1on.amlethmp.user.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Sh1on</a>
 */
public interface UserRepository extends R2dbcRepository<User, String> {
    Flux<User> findAllBy(Pageable pageable);
}
