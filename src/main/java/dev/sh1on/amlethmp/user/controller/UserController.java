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
import dev.myrlennia237.annotation.spring.ApiRequestBody;
import dev.myrlennia237.component.dto.PagedResponse;
import dev.myrlennia237.template.controller.java.AbstractCrudController;
import dev.sh1on.amlethmp.common.constant.AmlethMPEndpoint;
import dev.sh1on.amlethmp.user.dto.UserCreateDto;
import dev.sh1on.amlethmp.user.dto.UserDto;
import dev.sh1on.amlethmp.user.dto.UserUpdateDto;
import dev.sh1on.amlethmp.user.service.UserService;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * <b>[API Controller]</b> <br>
 * Lớp xử lý các HTTP request nhận được tới module {@link dev.sh1on.amlethmp.user.model.User User}.
 *
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@ApiController(
        path = AmlethMPEndpoint.User.BASE,
        moduleName = "User",
        description = "Module xử lý thông tin liên quan tới người dùng")
@RequiredArgsConstructor
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
    public Mono<ResponseEntity<UserDto>> findById(@PathVariable @ApiParameter(type = ParameterIn.PATH) UUID id) {
        return responseHelper.okOrNotFound(service.findById(id));
    }

    @ApiMethod(
            method = RequestMethod.POST,
            summary = "Thêm người dùng",
            description = "Thêm thông tin người dùng mới vào hệ thống")
    public Mono<ResponseEntity<UserDto>> create(
            @RequestBody @ApiRequestBody(description = "Đối tượng chứa thông tin của người dùng mới")
            UserCreateDto dto) {
        return responseHelper.created(service.insert(dto));
    }

    @ApiMethod(
            method = RequestMethod.PUT,
            path = AmlethMPEndpoint.User.BY_ID,
            summary = "Cập nhật dữ liệu người dùng",
            description = "Cập nhật thông tin người dùng mới vào hệ thống")
    public Mono<ResponseEntity<UserDto>> update(
            @PathVariable @ApiParameter(type = ParameterIn.PATH)
            UUID id,
            @RequestBody @ApiRequestBody(description = "Đối tượng chứa thông tin về dữ liệu mới sử dụng để cập nhật")
            UserUpdateDto dto) {

        return responseHelper.ok(service.update(id, dto));
    }

    @ApiMethod(
            method = RequestMethod.DELETE,
            path = AmlethMPEndpoint.User.BY_ID,
            summary = "Xóa người dùng",
            description = "Xóa hoàn toàn thông tin người dùng khỏi vào hệ thống")
    public Mono<ResponseEntity<Void>> delete(@PathVariable @ApiParameter(type = ParameterIn.PATH) UUID id) {
        return responseHelper.ok(service.deleteById(id));
    }

    @ApiMethod(
            method = RequestMethod.PATCH,
            path = AmlethMPEndpoint.User.DISABLE,
            summary = "Vô hiệu hóa người dùng",
            description = "Tạm thời vô hiệu hóa người dùng được chỉ định")
    public Mono<ResponseEntity<Void>> disable(@PathVariable @ApiParameter(type = ParameterIn.PATH) UUID id) {
        return responseHelper.ok(service.disable(id));
    }

    @ApiMethod(
            method = RequestMethod.PATCH,
            path = AmlethMPEndpoint.User.ENABLE,
            summary = "Kích hoạt lại người dùng",
            description = "Bỏ khả năng vô hiệu hóa khỏi người dùng")
    public Mono<ResponseEntity<Void>> enable(@PathVariable @ApiParameter(type = ParameterIn.PATH) UUID id) {
        return responseHelper.ok(service.enable(id));
    }
}
