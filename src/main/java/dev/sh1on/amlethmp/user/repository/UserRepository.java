package dev.sh1on.amlethmp.user.repository;

import org.springframework.stereotype.Repository;

import dev.myrlennia237.template.repository.ExtendedR2dbcRepository;
import dev.sh1on.amlethmp.user.model.User;
import reactor.core.publisher.Mono;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Repository
public interface UserRepository extends ExtendedR2dbcRepository<User> {
    Mono<User> findByEmail(String email);
}
