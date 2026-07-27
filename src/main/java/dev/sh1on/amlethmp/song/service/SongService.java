package dev.sh1on.amlethmp.song.service;

import dev.myrlennia237.annotation.spring.EffectiveReadOnlyTransactional;
import dev.myrlennia237.annotation.spring.EffectiveTransactional;
import dev.myrlennia237.component.dto.PagedResponse;
import dev.myrlennia237.template.service.java.AbstractCrudService;
import dev.sh1on.amlethmp.song.dto.SongCreateDto;
import dev.sh1on.amlethmp.song.dto.SongDto;
import dev.sh1on.amlethmp.song.dto.SongUpdateDto;
import dev.sh1on.amlethmp.song.mapper.SongMapper;
import dev.sh1on.amlethmp.song.model.Song;
import dev.sh1on.amlethmp.song.repository.SongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

/**
 * <b>[Domain Service]</b> <br>
 * Lớp xử lý logic API và nghiệp vụ cho module {@link Song}.
 *
 * @author <a href="https://github.com/henry0337">Muharux</a>
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Service
@RequiredArgsConstructor
public class SongService extends AbstractCrudService<SongDto, SongCreateDto, SongUpdateDto> {
    private final SongRepository repository;
    private final SongMapper mapper;

    @EffectiveReadOnlyTransactional
    public Mono<PagedResponse<SongDto>> findAll(Pageable pageable) {
        return repository.findAllBy(pageable)
                .switchIfEmpty(reactorHelper.emptyFlux())
                .map(mapper::toSongDto)
                .collectList()
                .zipWith(repository.count())
                .map(tuple -> PagedResponse.from(new PageImpl<>(tuple.getT1(), pageable, tuple.getT2())));
    }

    @EffectiveReadOnlyTransactional
    public Mono<SongDto> findById(UUID id) {
        return repository.findById(id).map(mapper::toSongDto);
    }

    @EffectiveTransactional
    public Mono<SongDto> insert(SongCreateDto dto) {
        return repository.save(mapper.toSong(dto)).map(mapper::toSongDto);
    }

    @EffectiveTransactional
    public Mono<SongDto> update(UUID id, SongUpdateDto dto) {
        return repository.findById(id)
                .flatMap((Song song) -> {
                    mapper.updateSong(dto, song);
                    return repository.save(song);
                })
                .map(mapper::toSongDto);
    }

    @EffectiveTransactional
    public Mono<Void> deleteById(UUID id) {
        return repository.deleteById(id);
    }

    @EffectiveTransactional
    public Mono<Void> disable(UUID id) {
        return repository.findById(id)
                .flatMap((Song song) -> auditorAware.getCurrentAuditor()
                        .flatMap((UUID auditor) -> {
                            song.markAsDisabled(auditor, Instant.now());
                            return reactorHelper.discardReturnValue(repository.save(song));
                        }));
    }

    @EffectiveTransactional
    public Mono<Void> enable(UUID id) {
        return repository.findById(id)
                .flatMap((Song song) -> {
                    song.restore();
                    return reactorHelper.discardReturnValue(repository.save(song));
                });
    }
}
