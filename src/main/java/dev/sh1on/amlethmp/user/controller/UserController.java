package dev.sh1on.amlethmp.user.controller;

import dev.sh1on.amlethmp.common.shared.SortPredicate;
import dev.sh1on.amlethmp.common.shared.constant.SortOrder;
import dev.sh1on.amlethmp.common.template.controller.AmlethMPRestController;
import dev.sh1on.amlethmp.common.utils.ControllerUtils;
import dev.sh1on.amlethmp.user.dto.UserCreateDto;
import dev.sh1on.amlethmp.user.dto.UserDto;
import dev.sh1on.amlethmp.user.dto.UserUpdateDto;
import dev.sh1on.amlethmp.user.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static dev.sh1on.amlethmp.user.UserRoute.BASE_USER_PATH;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Sh1on</a>
 */
@RestController
@RequestMapping(BASE_USER_PATH)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController implements AmlethMPRestController<UserDto, String, UserCreateDto, UserUpdateDto> {
    UserService service;
    ControllerUtils controllerUtils;

    @Override
    @GetMapping
    public Mono<ResponseEntity<Mono<Page<UserDto>>>> findAll(
            @RequestParam(defaultValue = "0") Integer offset,
            @RequestParam(defaultValue = "10") Integer limit,
            @RequestParam(defaultValue = "ASC") String order,
            @RequestParam(defaultValue = "") String prop) {
        Sort sort = Sort.unsorted();
        if (!order.isBlank() && !prop.isBlank()) {
            sort = Sort.by(Sort.Direction.fromString(order), prop);
        }

        PageRequest pageable = PageRequest.of(offset, limit, sort);
        return controllerUtils.awaitableOk(service.findAll(pageable));
    }

    @Override
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Mono<UserDto>>> findByKey(@PathVariable String id) {
        return controllerUtils.awaitableOk(service.findByKey(id));
    }

    @Override
    @PostMapping
    public Mono<ResponseEntity<Mono<UserDto>>> create(@RequestBody UserCreateDto dto) {
        return controllerUtils.awaitableCreated(service.save(dto));
    }

    @Override
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Mono<UserDto>>> update(@PathVariable String id, @RequestBody UserUpdateDto dto) {
        return controllerUtils.awaitableOk(service.update(id, dto));
    }

    @Override
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Mono<Void>>> delete(@PathVariable String id) {
        return controllerUtils.awaitableOk(service.deleteById(id).then());
    }
}
