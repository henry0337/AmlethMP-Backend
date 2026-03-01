package dev.sh1on.amlethmp.auth.controller;

import dev.sh1on.amlethmp.auth.AuthRoute;
import dev.sh1on.amlethmp.auth.dto.LoginRequest;
import dev.sh1on.amlethmp.auth.dto.RegisterRequest;
import dev.sh1on.amlethmp.auth.service.AuthService;
import dev.sh1on.amlethmp.user.dto.UserDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Patricia</a>
 */
@RestController
@RequestMapping(AuthRoute.BASE_AUTH_PATH)
@RequiredArgsConstructor
@Validated
@Tag(name = "Auth", description = "Mô-đun đảm nhiệm tác vụ xác thực thông tin đăng ký/đăng nhập của người dùng")
public class AuthController {
    private final AuthService service;

    /**
     * Phương thức đại diện request <b>POST</b> dùng để xác thực thông tin đăng nhập của người dùng.
     * @param request Đối tượng {@link LoginRequest} chứa email và mật khẩu.
     * @return {@link ResponseEntity} chứa token JWT nếu đăng nhập thành công, hoặc {@code 401 Unauthorized} nếu thất bại.
     */
    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@RequestBody @Valid LoginRequest request) {
        return service.login(request.getEmail(), request.getPassword())
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

    /**
     * Phương thức đại diện request <b>POST</b> dùng để đăng ký tài khoản người dùng mới.
     * @param user Đối tượng {@link RegisterRequest} chứa thông tin cần thiết để tạo người dùng.
     * @return {@link ResponseEntity} chứa {@link UserDto} của người dùng vừa được tạo (status 201 Created) nếu thành công,
     *         hoặc 400 Bad Request nếu thất bại.
     */
    @PostMapping("/register")
    public Mono<ResponseEntity<UserDto>> register(@RequestBody @Valid RegisterRequest user) {
        return service.register(user)
                .map(saved -> ResponseEntity.status(HttpStatus.CREATED).body(saved))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().build()));
    }
}
