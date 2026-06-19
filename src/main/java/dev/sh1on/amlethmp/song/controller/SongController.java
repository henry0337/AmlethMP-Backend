package dev.sh1on.amlethmp.song.controller;

import dev.sh1on.amlethmp.AmlethMPEndpoint;
import dev.sh1on.amlethmp.common.shared.dto.PagedResponse;
import dev.sh1on.amlethmp.common.template.controller.AmlethMPRestController;
import dev.sh1on.amlethmp.song.dto.SongCreateDto;
import dev.sh1on.amlethmp.song.dto.SongDto;
import dev.sh1on.amlethmp.song.dto.SongUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * @author <a href="https://github.com/henry0337">S3lena</a>
 */
@RestController
@RequestMapping(AmlethMPEndpoint.Song.BASE)
@RequiredArgsConstructor
public class SongController extends AmlethMPRestController<SongDto, UUID, SongCreateDto, SongUpdateDto> {

    @Override
    public Mono<ResponseEntity<PagedResponse<SongDto>>> findAll(Integer offset, Integer limit, String order, String prop) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<SongDto>> findByKey(UUID key) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<SongDto>> create(SongCreateDto dto) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<SongDto>> update(UUID key, SongUpdateDto dto) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<Void>> delete(UUID key) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<Void>> disable(UUID key) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<Void>> enable(UUID key) {
        return null;
    }
}
