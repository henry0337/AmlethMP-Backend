package dev.sh1on.amlethmp.song.controller;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;

import dev.myrlennia237.annotation.spring.ApiController;
import dev.myrlennia237.annotation.spring.ApiMethod;
import dev.myrlennia237.annotation.spring.ApiParameter;
import dev.myrlennia237.annotation.spring.ApiRequestBody;
import dev.myrlennia237.component.dto.PagedResponse;
import dev.myrlennia237.template.controller.java.AbstractCrudController;
import dev.sh1on.amlethmp.common.shared.constant.AmlethMPEndpoint;
import dev.sh1on.amlethmp.song.dto.SongCreateDto;
import dev.sh1on.amlethmp.song.dto.SongDto;
import dev.sh1on.amlethmp.song.dto.SongUpdateDto;
import dev.sh1on.amlethmp.song.service.SongService;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * <b>[API Controller]</b> <br>
 * Lớp giao tiếp với các yêu cầu HTTP thuộc module {@link dev.sh1on.amlethmp.song.model.Song Song}.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@ApiController(
        path = AmlethMPEndpoint.Song.BASE,
        moduleName = "Song",
        description = "Mô-đun xử lý thông tin liên quan tới bài hát")
@RequiredArgsConstructor
@SuppressWarnings("java:S6856")
public class SongController extends AbstractCrudController<SongDto, SongCreateDto, SongUpdateDto> {
    private final SongService service;

    @ApiMethod(method = RequestMethod.GET)
    public Mono<ResponseEntity<PagedResponse<SongDto>>> findAll(@ApiRequestBody Pageable pageable) {
        return responseHelper.ok(service.findAll(pageable));
    }

    @ApiMethod(method = RequestMethod.GET, path = AmlethMPEndpoint.Song.BY_ID)
    public Mono<ResponseEntity<SongDto>> findById(
            @ApiParameter(type = ParameterIn.PATH) @PathVariable UUID id) {
        return responseHelper.okOrNotFound(service.findById(id));
    }

    @ApiMethod(method = RequestMethod.POST)
    public Mono<ResponseEntity<SongDto>> create(@RequestBody SongCreateDto dto) {
        return responseHelper.created(service.insert(dto));
    }

    @ApiMethod(method = RequestMethod.PUT, path = AmlethMPEndpoint.Song.BY_ID)
    public Mono<ResponseEntity<SongDto>> update(
            @ApiParameter(type = ParameterIn.PATH) @PathVariable UUID id,
            @RequestBody SongUpdateDto dto) {
        return responseHelper.ok(service.update(id, dto));
    }

    @ApiMethod(method = RequestMethod.DELETE, path = AmlethMPEndpoint.Song.BY_ID)
    public Mono<ResponseEntity<Void>> delete(
            @ApiParameter(type = ParameterIn.PATH) @PathVariable UUID id) {
        return responseHelper.noContent(service.deleteById(id));
    }

    @ApiMethod(method = RequestMethod.POST, path = AmlethMPEndpoint.Song.DISABLE)
    public Mono<ResponseEntity<Void>> disable(
            @ApiParameter(type = ParameterIn.PATH) @PathVariable UUID id) {
        return responseHelper.okEmpty(service.disable(id));
    }

    @ApiMethod(method = RequestMethod.POST, path = AmlethMPEndpoint.Song.ENABLE)
    public Mono<ResponseEntity<Void>> enable(
            @ApiParameter(type = ParameterIn.PATH) @PathVariable UUID id) {
        return responseHelper.okEmpty(service.enable(id));
    }
}
