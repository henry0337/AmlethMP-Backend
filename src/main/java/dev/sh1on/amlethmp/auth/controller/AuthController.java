package dev.sh1on.amlethmp.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import dev.myrlennia237.annotation.spring.ApiController;
import dev.myrlennia237.annotation.spring.ApiMethod;
import dev.myrlennia237.helper.ResponseHelper;
import dev.myrlennia237.template.controller.ReactiveController;
import dev.sh1on.amlethmp.auth.dto.LoginRequest;
import dev.sh1on.amlethmp.auth.dto.RegisterRequest;
import dev.sh1on.amlethmp.auth.service.AuthService;
import dev.sh1on.amlethmp.common.constant.AmlethMPEndpoint;
import dev.sh1on.amlethmp.user.dto.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * <b>[API Controller]</b> <br>
 * Lớp xử lý các HTTP request nhận được tới module <b>Auth</b>.
 * 
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
@ApiController(
        path = AmlethMPEndpoint.Auth.BASE,
        moduleName = "Auth",
        description = "Module đảm nhiệm tác vụ xử lý đăng ký/đăng nhập của người dùng")
@Validated
@RequiredArgsConstructor
public class AuthController extends ReactiveController {

    private final ResponseHelper responseHelper;
    private final AuthService service;

    @ApiMethod(
            method = RequestMethod.POST,
            path = AmlethMPEndpoint.Auth.LOGIN,
            summary = "Đăng nhập",
            description = "",
            consumeType = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produceType = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<String>> login(@RequestBody @Valid LoginRequest request) {
        return responseHelper.ok(service.login(request.getEmail(), request.getPassword()))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }

    @ApiMethod(
            method = RequestMethod.POST,
            path = AmlethMPEndpoint.Auth.REGISTER,
            summary = "Đăng ký",
            description = "",
            consumeType = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produceType = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<UserDto>> register(@RequestBody @Valid RegisterRequest user) {
        return responseHelper.created(service.register(user))
                .onErrorResume(e -> Mono.just(ResponseEntity.badRequest().build()));
    }

    @ApiMethod(
            method = RequestMethod.POST,
            path = AmlethMPEndpoint.Auth.LOGOUT,
            summary = "Đăng xuất",
            description = "Thu hồi JWT đang được sử dụng, token đó sẽ bị từ chối ở mọi yêu cầu tiếp theo.")
    public Mono<ResponseEntity<Void>> logout() {
        return responseHelper.okEmpty(service.logout())
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()));
    }
}
