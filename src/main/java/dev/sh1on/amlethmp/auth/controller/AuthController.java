package dev.sh1on.amlethmp.auth.controller;

import dev.myrlennia237.annotation.spring.ApiController;
import dev.myrlennia237.annotation.spring.ApiMethod;
import dev.myrlennia237.template.controller.ReactiveRestController;
import dev.myrlennia237.helper.ResponseHelper;
import dev.sh1on.amlethmp.common.AmlethMPEndpoint;
import dev.sh1on.amlethmp.auth.dto.LoginRequest;
import dev.sh1on.amlethmp.auth.dto.RegisterRequest;
import dev.sh1on.amlethmp.auth.service.AuthService;
import dev.sh1on.amlethmp.user.dto.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Mono;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@ApiController(
        path = AmlethMPEndpoint.Auth.BASE,
        moduleName = "Auth",
        description = "Mô-đun đảm nhiệm tác vụ xác thực thông tin đăng ký/đăng nhập của người dùng")
@Validated
@RequiredArgsConstructor
@SuppressWarnings("java:S6856")
public class AuthController extends ReactiveRestController {
    private final ResponseHelper responseHelper;
    private final AuthService service;

    /**
     * Phương thức đại diện request <b>POST</b> dùng để xác thực thông tin đăng nhập của người dùng.
     *
     * @param request Đối tượng {@link LoginRequest} chứa email và mật khẩu.
     * @return {@link ResponseEntity} chứa token JWT nếu đăng nhập thành công, hoặc {@code 401 Unauthorized} nếu thất bại.
     */
    @ApiMethod(method = RequestMethod.POST, path = AmlethMPEndpoint.Auth.LOGIN, endpointSummary ="Đăng nhập")
    public Mono<ResponseEntity<String>> login(@RequestBody @Valid LoginRequest request) {
        return responseHelper.awaitOk(service.login(request.getEmail(), request.getPassword()))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

    /**
     * Phương thức đại diện request <b>POST</b> dùng để đăng ký tài khoản người dùng mới.
     *
     * @param user Đối tượng {@link RegisterRequest} chứa thông tin cần thiết để tạo người dùng.
     * @return {@link ResponseEntity} chứa {@link UserDto} của người dùng vừa được tạo (status 201 Created) nếu thành công,
     * hoặc 400 Bad Request nếu thất bại.
     */
    @ApiMethod(method = RequestMethod.POST, path = AmlethMPEndpoint.Auth.REGISTER, endpointSummary ="Đăng ký")
    public Mono<ResponseEntity<UserDto>> register(@RequestBody @Valid RegisterRequest user) {
        return responseHelper.awaitCreated(service.register(user))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().build()));
    }
}
