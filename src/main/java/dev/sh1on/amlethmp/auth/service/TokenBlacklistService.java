package dev.sh1on.amlethmp.auth.service;

import dev.sh1on.amlethmp.common.shared.service.RedisService;
import dev.sh1on.amlethmp.common.template.service.AmlethMPService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Service
@RequiredArgsConstructor
public class TokenBlacklistService extends AmlethMPService {
    private final RedisService redisService;

    public Mono<Boolean> blacklistToken(String token) {
        return redisService.setPermanent("", token);
    }

    /**
     * Kiểm tra xem liệu {@code token} được chỉ định đã bị blacklist chưa.
     * @param token Token được kiểm tra
     * @return Trả về {@code true}/{@code false} tương ứng.
     */
    public Mono<Boolean> isBlacklisted(String token) {
        return redisService.contains("");
    }

}
