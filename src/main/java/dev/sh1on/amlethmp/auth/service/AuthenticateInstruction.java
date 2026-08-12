package dev.sh1on.amlethmp.auth.service;

import dev.sh1on.amlethmp.auth.dto.RegisterRequest;
import dev.sh1on.amlethmp.user.dto.UserDto;
import reactor.core.publisher.Mono;

/**
 * <b>[Logical Interface]</b> <br>
 * Interface đảm nhiệm chức năng liên quan tới <b>xác thực</b>.
 * <p>
 * <b>Ghi chú:</b> Interface này chỉ sử dụng nội bộ trong module <b>Auth</b>.
 * </p>
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
public interface AuthenticateInstruction {

    /**
     * Nhận, validate và xác nhận yêu cầu xác thực đăng nhập lên từ phía client.
     * @param email     Email dùng để xác thực
     * @param password  Mật khẩu đăng nhập
     * @return JWT token dành riêng cho người dùng yêu cầu.
     */
    Mono<String> login(String email, String password);

    /**
     * Nhận, validate và xác nhận yêu cầu thêm thông tin đăng nhập mới vào hệ thống từ phía client.
     * @param dto Thông tin người dùng mới cần thêm vào hệ thống
     * @return Thông tin cơ bản của người dùng mới.
     */
    Mono<UserDto> register(RegisterRequest dto);

    /**
     * Đăng xuất người dùng đang thực hiện yêu cầu.
     *
     * @return {@link Mono} hoàn tất khi token đã được thu hồi.
     */
    Mono<Void> logout();
}
