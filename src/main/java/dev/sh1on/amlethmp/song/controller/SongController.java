package dev.sh1on.amlethmp.song.controller;

import dev.sh1on.amlethmp.common.template.controller.AmlethMPRestController;
import dev.sh1on.amlethmp.song.dto.SongCreateDto;
import dev.sh1on.amlethmp.song.dto.SongDto;
import dev.sh1on.amlethmp.song.dto.SongUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author <a href="https://github.com/henry0337">S3lena</a>
 */
@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class SongController extends AmlethMPRestController<SongDto, String, SongCreateDto, SongUpdateDto> {

    @Override
    public Mono<ResponseEntity<Mono<Page<SongDto>>>> findAll(Integer offset, Integer limit, String order, String prop) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<Mono<SongDto>>> findByKey(String key) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<Mono<SongDto>>> create(SongCreateDto dto) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<Mono<SongDto>>> update(String key, SongUpdateDto dto) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<Mono<Void>>> delete(String key) {
        return null;
    }

    @Override
    public Mono<ResponseEntity<Mono<Void>>> disable(String key) {
        return null;
    }
}
