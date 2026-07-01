package dev.sh1on.amlethmp.user.controller;

import dev.myrlennia237.annotation.spring.ApiController;
import dev.myrlennia237.annotation.spring.ApiMethod;
import dev.myrlennia237.annotation.spring.ApiParameter;
import dev.myrlennia237.component.dto.PagedResponse;
import dev.myrlennia237.template.controller.java.AbstractCrudController;
import dev.sh1on.amlethmp.common.AmlethMPEndpoint;
import dev.sh1on.amlethmp.user.dto.UserCreateDto;
import dev.sh1on.amlethmp.user.dto.UserDto;
import dev.sh1on.amlethmp.user.dto.UserUpdateDto;
import dev.sh1on.amlethmp.user.service.UserService;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Mono;

import java.util.UUID;

@ApiController(
        path = AmlethMPEndpoint.User.BASE,
        moduleName = "User",
        description = "Mô-đun xử lý thông tin liên quan tới người dùng")
@RequiredArgsConstructor
@SuppressWarnings("java:S6856")
public class UserController extends AbstractCrudController<UserDto, UserCreateDto, UserUpdateDto> {
    private final UserService service;

    @ApiMethod(method = RequestMethod.GET)
    public Mono<ResponseEntity<PagedResponse<UserDto>>> findAll(Pageable pageable) {
        return responseHelper.awaitOk(service.findAll(pageable));
    }

    @ApiMethod(method = RequestMethod.GET, path = AmlethMPEndpoint.User.BY_ID)
    public Mono<ResponseEntity<UserDto>> findById(
            @ApiParameter(name = "id", type = ParameterIn.PATH) @PathVariable UUID id) {
        return responseHelper.awaitOrNotFound(service.findById(id));
    }

    @ApiMethod(method = RequestMethod.POST)
    public Mono<ResponseEntity<UserDto>> create(@RequestBody UserCreateDto dto) {
        return responseHelper.awaitCreated(service.insert(dto));
    }

    @ApiMethod(method = RequestMethod.PUT, path = AmlethMPEndpoint.User.BY_ID)
    public Mono<ResponseEntity<UserDto>> update(
            @ApiParameter(name = "id", type = ParameterIn.PATH) @PathVariable UUID id,
            @RequestBody UserUpdateDto dto) {
        return responseHelper.awaitOk(service.update(id, dto));
    }

    @ApiMethod(method = RequestMethod.DELETE, path = AmlethMPEndpoint.User.BY_ID)
    public Mono<ResponseEntity<Void>> delete(
            @ApiParameter(name = "id", type = ParameterIn.PATH) @PathVariable UUID id) {
        return responseHelper.awaitNoContent(service.deleteById(id));
    }

    @ApiMethod(method = RequestMethod.DELETE, path = AmlethMPEndpoint.User.DISABLE)
    public Mono<ResponseEntity<Void>> disable(
            @ApiParameter(name = "id", type = ParameterIn.PATH) @PathVariable UUID id) {
        return responseHelper.awaitNoContent(service.disable(id));
    }

    @ApiMethod(method = RequestMethod.POST, path = AmlethMPEndpoint.User.ENABLE)
    public Mono<ResponseEntity<Void>> enable(
            @ApiParameter(name = "id", type = ParameterIn.PATH) @PathVariable UUID id) {
        return responseHelper.awaitNoContent(service.enable(id));
    }
}
