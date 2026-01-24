package dev.sh1on.amlethmp.user.repository;

import dev.sh1on.amlethmp.user.model.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Sh1on</a>
 */
public interface UserRepository extends ReactiveCrudRepository<User, String>, ReactiveSortingRepository<User, String> { }
