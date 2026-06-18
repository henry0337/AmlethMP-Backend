package dev.sh1on.amlethmp.user.repository;

import dev.sh1on.amlethmp.common.template.repository.AmlethMPRepository;
import dev.sh1on.amlethmp.user.model.User;
import reactor.core.publisher.Mono;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
public interface UserRepository extends AmlethMPRepository<User, String> {
    Mono<User> findByEmail(String email);
}
