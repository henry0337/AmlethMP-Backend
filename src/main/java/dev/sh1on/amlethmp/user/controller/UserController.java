package dev.sh1on.amlethmp.user.controller;

import dev.sh1on.amlethmp.common.template.controller.AmlethMPController;
import dev.sh1on.amlethmp.common.utils.ReactorUtils;
import dev.sh1on.amlethmp.user.dto.UserCreateDto;
import dev.sh1on.amlethmp.user.dto.UserDto;
import dev.sh1on.amlethmp.user.dto.UserUpdateDto;
import dev.sh1on.amlethmp.user.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static dev.sh1on.amlethmp.user.UserRoute.BASE_USER_PATH;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Sh1on</a>
 */
@RestController
@RequestMapping(BASE_USER_PATH)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController implements AmlethMPController<UserDto, String, UserCreateDto, UserUpdateDto> {
    UserService service;
    ReactorUtils reactorUtils;

    @Override
    @GetMapping
    public Mono<ResponseEntity<Flux<UserDto>>> findAll(Pageable pageable, Sort sort) {
        return reactorUtils.awaitableOk(service.findAll(pageable, sort));
    }

    @Override
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Mono<UserDto>>> obtainByKey(@PathVariable String id) {
        return Mono.just(new ResponseEntity<>(service.findByKey(id), HttpStatusCode.valueOf(201)));
    }

    @Override
    @PostMapping
    public Mono<ResponseEntity<Mono<UserDto>>> create(UserCreateDto dto) {
        return null;
    }

    @Override
    @PutMapping("/{id}")
    public Mono<ResponseEntity<Mono<UserDto>>> update(String id, UserUpdateDto dto) {
        return Mono.just(ResponseEntity.ok(service.update(id, dto)));
    }

    @Override
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Mono<Void>>> delete(String id) {
        service.deleteById(id);
        return Mono.just(ResponseEntity.ok().build());
    }
}
