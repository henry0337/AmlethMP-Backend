package dev.sh1on.amlethmp.user.repository;

import dev.myrlennia237.template.repository.ModifiedR2dbcRepository;
import dev.sh1on.amlethmp.user.model.User;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Repository
public interface UserRepository extends ModifiedR2dbcRepository<User> {
    Mono<User> findByEmail(String email);
}
