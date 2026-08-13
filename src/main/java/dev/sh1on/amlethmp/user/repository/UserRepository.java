package dev.sh1on.amlethmp.user.repository;

import dev.myrlennia237.template.repository.ExtendedR2dbcRepository;
import dev.sh1on.amlethmp.user.model.User;
import reactor.core.publisher.Mono;

/**
 * <b>[API Repository]</b> <br>
 * Interface chứa các phương thức truy vấn database cho entity {@link User}.
 * 
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
public interface UserRepository extends ExtendedR2dbcRepository<User> {
    /**
     * Tìm kiếm thông tin người dùng hệ thống theo {@code email}.
     * 
     * @param email Email đã đăng ký với hệ thống
     * @return {@link Mono} phát ra thông tin người dùng đã đăng ký trong hệ thống, không thì trả về {@link Mono#empty()}.
     */
    Mono<User> findByEmail(String email);
}
