package dev.sh1on.amlethmp.user.controller;

import dev.sh1on.amlethmp.common.template.controller.AmlethMPController;
import dev.sh1on.amlethmp.user.model.User;
import dev.sh1on.amlethmp.user.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static dev.sh1on.amlethmp.user.route.UserRoute.BASE_USER_PATH;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Sh1on</a>
 */
@RestController
@RequestMapping(BASE_USER_PATH)
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController implements BaseApiController<User, String> {
    UserService service;

    @Override
    @GetMapping
    public Flux<User> getAll() {
        return service.getAll();
    }

    @Override
    @GetMapping("/{id}")
    public Mono<User> getById(String id) {
        return service.getById(id);
    }

    @Override
    @PostMapping
    public Mono<User> create(User entity) {
        return service.create(entity);
    }

    @Override
    @PutMapping("/{id}")
    public Mono<User> update(String id, User entity) {
        return service.update(id, entity);
    }

    @Override
    @DeleteMapping("/{id}")
    public Mono<Void> delete(String id) {
        return service.delete(id);
    }
}
