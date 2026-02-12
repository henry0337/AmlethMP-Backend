package dev.sh1on.amlethmp.auth.controller;

import dev.sh1on.amlethmp.common.template.controller.AmlethMPAuthController;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

/**
 *
 * @author <a href="https://github.com/AdorableDandelion25">Stella</a>
 * @param <I1>
 * @param <O1>
 * @param <I2>
 * @param <O2>
 */
public interface JwtAuthencationController<I1, O1, I2, O2> extends AmlethMPAuthController {
    Mono<ResponseEntity<O2>> login(I2 request);
    Mono<ResponseEntity<O1>> register(I1 request);
}