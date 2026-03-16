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

/**
 * @author <a href="https://github.com/henry0337">S3lena</a>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class SongService
        extends AmlethMPRestService<SongDto, String, SongCreateDto, SongUpdateDto>
        implements Reversible<String> {
    public Mono<Void> deleteById(String key) {
        return null;
    }

    public Mono<SongDto> save(SongCreateDto dto) {
        return null;
    }

    public Mono<SongDto> update(String key, SongUpdateDto dto) {
        return null;
    }

    public Mono<SongDto> findByKey(String key) {
        return null;
    }

    public Mono<Page<SongDto>> findAll(Pageable pageable) {
        return null;
    }

    public Mono<Void> disableById(String key) {
        return null;
    }
}
