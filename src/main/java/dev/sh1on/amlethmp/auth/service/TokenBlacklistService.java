package dev.sh1on.amlethmp.auth.service;

import dev.sh1on.amlethmp.common.utils.RedisUtils;
import lombok.RequiredArgsConstructor;
//import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Patricia</a>
 */
@Service
@RequiredArgsConstructor
public class TokenBlacklistService {
    private final RedisUtils redisUtils;

    public Mono<Boolean> blacklistToken(String token) {
        return redisUtils.setPermanent("", token);
    }

    /**
     * Kiểm tra xem liệu {@code token} được chỉ định đã bị blacklist chưa.
     * @param token Token được kiểm tra
     * @return Trả về {@code true}/{@code false} tương ứng.
     */
    public Mono<Boolean> isBlacklisted(String token) {
//        return redisTemplate.hasKey(token);
        return Mono.just(true);
    }

}
