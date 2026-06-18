package dev.sh1on.amlethmp.common.shared.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisUtils {
    private final ReactiveRedisTemplate<String, String> redisTemplate;
    private final ReactorUtils reactorUtils;

    /**
     * Lưu giá trị vào Redis với key vĩnh viễn (không hết hạn).
     *
     * @param key   Khóa Redis
     * @param value Giá trị cần lưu
     * @return {@code true} nếu lưu thành công, không thì {@code false}.
     * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
     */
    public Mono<Boolean> setPermanent(String key, String value) {
        return redisTemplate.opsForValue().set(key, value);
    }

    /**
     * Lưu cặp key-value vào Redis với thời hạn được xác định bởi {@code ttl}. <br>
     *
     * @param key   Khóa được lưu trong Redis
     * @param value Giá trị cần lưu vào {@code key}
     * @param ttl   Thời hạn hiệu lực của dữ liệu trên
     * @return {@code true} nếu lưu thành công, {@code false} nếu tham số {@code ttl} không hợp lệ hoặc không lưu
     * thành công.
     * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
     */
    public Mono<Boolean> setTemporal(String key, String value, Duration ttl) {
        if (ttl.isZero() || ttl.isNegative()) {
            log.warn("Invalid TTL provided for Redis key [{}]: {}", key, ttl);
            return reactorUtils.single(false);
        }

        return redisTemplate.opsForValue().set(key, value, ttl);
    }

    /**
     * Lấy giá trị từ Redis theo key.
     *
     * @param key Khóa Redis cần truy vấn
     * @return Giá trị tương ứng hoặc {@code null} nếu không tồn tại.
     * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
     */
    public Mono<String> getValueFor(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Mono<Boolean> contains(String key) {
        return redisTemplate.hasKey(key);
    }
}
