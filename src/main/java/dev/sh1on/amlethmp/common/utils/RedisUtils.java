package dev.sh1on.amlethmp.common.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisUtils {
    private final ReactiveRedisTemplate<String, String> redisTemplate;

    /**
     * Lưu giá trị vào Redis với key vĩnh viễn (không hết hạn).
     *
     * @param key   Khóa Redis
     * @param value Giá trị cần lưu
     * @return {@code true} nếu lưu thành công, không thì {@code false}.
     * @author <a href="https://github.com/AdorableDandelion25">Patricia</a>
     */
    public Mono<Boolean> setPermanent(String key, String value) {
        return redisTemplate.opsForValue().set(key, value);
    }

    /**
     * Lưu giá trị vào Redis với thời gian sống (TTL) xác định. <br>
     * Nếu {@code ttl} null, zero hoặc âm → không lưu và trả về {@code false}.
     *
     * @param key   Khóa Redis
     * @param value Giá trị cần lưu
     * @param ttl   Thời gian sống (có thể null)
     * @return {@code true} nếu lưu thành công, {@code false} nếu tham số {@code ttl} không hợp lệ hoặc không lưu
     * thành công.
     * @author <a href="https://github.com/AdorableDandelion25">Patricia</a>
     */
    public Mono<Boolean> setTemporal(String key, String value, @Nullable Duration ttl) {
        if (ttl == null) {
            log.warn("");
            return Mono.just(false);
        }

        if (ttl.isZero() || ttl.isNegative()) {
            log.warn("");
            return Mono.just(false);
        }

        return redisTemplate.opsForValue().set(key, value, ttl);
    }

    /**
     * Lấy giá trị từ Redis theo key.
     *
     * @param key Khóa Redis cần truy vấn
     * @return Giá trị tương ứng hoặc {@code null} nếu không tồn tại.
     * @author <a href="https://github.com/AdorableDandelion25">Patricia</a>
     */
    public Mono<String> get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Mono<Boolean> contains(String key) {
        // TODO
        return Mono.just(true);
    }
}
