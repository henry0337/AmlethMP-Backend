package dev.sh1on.amlethmp.common.shared.controller;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Sh1on</a>
 * @param <T> (WIP)
 * @param <ID> (WIP)
 */
@SuppressWarnings("unused")
public interface BaseApiController<T, ID> {
    Flux<T> getAll();
    Mono<T> getById(ID id);
    Mono<T> create(T entity);
    Mono<T> update(ID id, T entity);
    Mono<Void> delete(ID id);
}
