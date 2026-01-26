package dev.sh1on.amlethmp.common.template.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Sh1on</a>
 * @param <T> (WIP)
 * @param <ID> (WIP)
 */
public interface BaseApiService<T, ID> {
    Flux<T> getAll();
    Mono<T> getById(ID id);
    Mono<T> create(T entity);
    Mono<T> update(ID id, T entity);
    Mono<Void> delete(ID id);
}
