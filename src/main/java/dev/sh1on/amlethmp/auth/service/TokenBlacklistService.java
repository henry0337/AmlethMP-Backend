package dev.sh1on.amlethmp.auth.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.HexFormat;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import dev.myrlennia237.service.ReactiveRedisService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import reactor.core.publisher.Mono;

/**
 * <b>[API Service]</b> <br>
 * Lớp quản lý danh sách các token đã bị <b>thu hồi</b> (blacklist), được lưu trong Redis.
 * <p>
 * Token không được lưu ở dạng thô: khóa Redis là bản băm <b>SHA-256</b> của token, còn giá trị chỉ là một cờ đánh dấu.
 * Mỗi khóa được gán TTL bằng thời gian còn lại của token, nên Redis sẽ tự dọn dẹp khi token hết hạn.
 * </p>
 *
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Service
@RequiredArgsConstructor
public class TokenBlacklistService {
    private static final String KEY_PREFIX = "auth:blacklist:";
    private static final String HASH_ALGORITHM = "SHA-256";
    private static final String REVOKED_MARKER = "1";

    private final ReactiveRedisService redisService;

    /**
     * Đưa {@code token} được chỉ định vào blacklist trong khoảng thời gian {@code ttl}.
     *
     * @param token Token cần thu hồi
     * @param ttl   Thời lượng token đó bị giữ trong blacklist, thường là thời gian còn lại cho tới lúc token hết hạn
     * @return Trả về {@code true} nếu ghi thành công, {@code false} nếu {@code ttl} không hợp lệ (bằng hoặc nhỏ hơn 0)
     * hay có lỗi khác xảy ra.
     */
    public Mono<Boolean> blacklistToken(String token, @Nullable Duration ttl) {
        Assert.hasText(token, "Token must not be empty");
        Assert.notNull(ttl, "TTL must not be null");
        return redisService.set(key(token), REVOKED_MARKER, ttl);
    }

    /**
     * Kiểm tra xem liệu {@code token} được chỉ định đã bị blacklist chưa.
     *
     * @param token Token được kiểm tra
     * @return Trả về {@code true}/{@code false} tương ứng.
     */
    public Mono<Boolean> isBlacklisted(String token) {
        Assert.hasText(token, "Token must not be empty");
        return redisService.get(key(token)).hasElement();
    }

    private static String key(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return KEY_PREFIX + HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Thuật toán " + HASH_ALGORITHM + " không khả dụng trên JVM hiện tại!", e);
        }
    }
}
