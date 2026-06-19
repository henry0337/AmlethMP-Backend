package dev.sh1on.amlethmp.song.service;

import dev.sh1on.amlethmp.common.template.service.AmlethMPRestService;
import dev.sh1on.amlethmp.common.template.service.crud.Reversible;
import dev.sh1on.amlethmp.song.dto.SongCreateDto;
import dev.sh1on.amlethmp.song.dto.SongDto;
import dev.sh1on.amlethmp.song.dto.SongUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * @author <a href="https://github.com/henry0337">S3lena</a>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SongService extends AmlethMPRestService<SongDto, UUID, SongCreateDto, SongUpdateDto>
        implements Reversible<UUID> {
    public Mono<Void> deleteById(UUID key) {
        return null;
    }

    public Mono<SongDto> save(SongCreateDto dto) {
        return null;
    }

    public Mono<SongDto> update(UUID key, SongUpdateDto dto) {
        return null;
    }

    public Mono<SongDto> findByKey(UUID key) {
        return null;
    }

    public Mono<Page<SongDto>> findAll(Pageable pageable) {
        return null;
    }

    public Mono<Void> disableById(UUID key) {
        return null;
    }

    public Mono<Void> enableById(UUID key) {
        return null;
    }
}
