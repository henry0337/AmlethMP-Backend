package dev.sh1on.amlethmp.user.controller;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import dev.myrlennia237.annotation.spring.ApiController;
import dev.myrlennia237.annotation.spring.ApiMethod;
import dev.myrlennia237.annotation.spring.ApiParameter;
import dev.myrlennia237.component.dto.PagedResponse;
import dev.myrlennia237.template.controller.java.AbstractCrudController;
import dev.sh1on.amlethmp.common.shared.constant.AmlethMPEndpoint;
import dev.sh1on.amlethmp.user.dto.UserCreateDto;
import dev.sh1on.amlethmp.user.dto.UserDto;
import dev.sh1on.amlethmp.user.dto.UserUpdateDto;
import dev.sh1on.amlethmp.user.service.UserService;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * <b>[API Controller]</b> <br>
 * Lớp xử lý các HTTP request nhận được tới module {@link User}.
 * 
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@ApiController(
        path = AmlethMPEndpoint.User.BASE,
        moduleName = "User",
        description = "Module xử lý thông tin liên quan tới người dùng")
@RequiredArgsConstructor
@SuppressWarnings("java:S6856")
public class UserController extends AbstractCrudController<UserDto, UserCreateDto, UserUpdateDto> {

    private final UserService service;

    @ApiMethod(
            method = RequestMethod.GET,
            summary = "Danh sách người dùng",
            description = "Lấy ra danh sách thông tin toàn bộ người dùng (đã được phân trang)")
    public Mono<ResponseEntity<PagedResponse<UserDto>>> findAll(Pageable pageable) {
        return responseHelper.ok(service.findAll(pageable));
    }

    @ApiMethod(
            method = RequestMethod.GET, 
            path = AmlethMPEndpoint.User.BY_ID,
            summary = "Thông tin người dùng cụ thể",
            description = "Lấy ra thông tin người dùng theo id được chỉ định")
    public Mono<ResponseEntity<UserDto>> findById(
            @ApiParameter(type = ParameterIn.PATH) @PathVariable UUID id) {
        return responseHelper.okOrNotFound(service.findById(id));
    }

    @ApiMethod(
            method = RequestMethod.POST,
            summary = "Thêm người dùng",
            description = "Thêm thông tin người dùng mới vào hệ thống")
    public Mono<ResponseEntity<UserDto>> create(@RequestBody UserCreateDto dto) {
        return responseHelper.created(service.insert(dto));
    }

    @ApiMethod(
            method = RequestMethod.PUT, 
            path = AmlethMPEndpoint.User.BY_ID,
            summary = "Thêm người dùng",
            description = "Thêm thông tin người dùng mới vào hệ thống")
    public Mono<ResponseEntity<UserDto>> update(
            @ApiParameter(type = ParameterIn.PATH) @PathVariable UUID id,
            @RequestBody UserUpdateDto dto) {
        return responseHelper.ok(service.update(id, dto));
    }

    @ApiMethod(method = RequestMethod.DELETE, path = AmlethMPEndpoint.User.BY_ID)
    public Mono<ResponseEntity<Void>> delete(
            @ApiParameter(type = ParameterIn.PATH) @PathVariable UUID id) {
        return responseHelper.ok(service.deleteById(id));
    }

    @ApiMethod(method = RequestMethod.POST, path = AmlethMPEndpoint.User.DISABLE)
    public Mono<ResponseEntity<Void>> disable(
            @ApiParameter(type = ParameterIn.PATH) @PathVariable UUID id) {
        return responseHelper.ok(service.disable(id));
    }

    @ApiMethod(method = RequestMethod.POST, path = AmlethMPEndpoint.User.ENABLE)
    public Mono<ResponseEntity<Void>> enable(
            @ApiParameter(type = ParameterIn.PATH) @PathVariable UUID id) {
        return responseHelper.ok(service.enable(id));
    }
}
