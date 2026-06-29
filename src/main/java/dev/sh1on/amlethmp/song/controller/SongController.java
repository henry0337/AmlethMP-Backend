package dev.sh1on.amlethmp.song.controller;

import dev.myrlennia237.annotation.spring.ApiController;
import dev.myrlennia237.annotation.spring.ApiMethod;
import dev.myrlennia237.annotation.spring.ApiParameter;
import dev.myrlennia237.component.dto.PagedResponse;
import dev.myrlennia237.template.controller.java.AbstractCrudController;
import dev.sh1on.amlethmp.common.AmlethMPEndpoint;
import dev.sh1on.amlethmp.song.dto.SongCreateDto;
import dev.sh1on.amlethmp.song.dto.SongDto;
import dev.sh1on.amlethmp.song.dto.SongUpdateDto;
import dev.sh1on.amlethmp.song.service.SongService;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * @author <a href="https://github.com/henry0337">Muharux</a>
 */
@ApiController(
        path = AmlethMPEndpoint.Song.BASE,
        moduleName = "Song",
        description = "Mô-đun xử lý thông tin liên quan tới bài hát")
@RequiredArgsConstructor
public class SongController extends AbstractCrudController<SongDto, SongCreateDto, SongUpdateDto> {
    private final SongService service;

    @ApiMethod(method = RequestMethod.GET)
    public Mono<ResponseEntity<PagedResponse<SongDto>>> findAll(Pageable pageable) {
        return responseHelper.awaitOk(service.findAll(pageable));
    }

    @ApiMethod(method = RequestMethod.GET, path = AmlethMPEndpoint.Song.BY_ID)
    public Mono<ResponseEntity<SongDto>> findById(
            @ApiParameter(name = "id", type = ParameterIn.PATH) @PathVariable UUID id) {
        return responseHelper.awaitOrNotFound(service.findById(id));
    }

    @ApiMethod(method = RequestMethod.POST)
    public Mono<ResponseEntity<SongDto>> create(@RequestBody SongCreateDto dto) {
        return responseHelper.awaitCreated(service.insert(dto));
    }

    @ApiMethod(method = RequestMethod.PUT, path = AmlethMPEndpoint.Song.BY_ID)
    public Mono<ResponseEntity<SongDto>> update(
            @ApiParameter(name = "id", type = ParameterIn.PATH) @PathVariable UUID id,
            @RequestBody SongUpdateDto dto) {
        return responseHelper.awaitOk(service.update(id, dto));
    }

    @ApiMethod(method = RequestMethod.DELETE, path = AmlethMPEndpoint.Song.BY_ID)
    public Mono<ResponseEntity<Void>> delete(
            @ApiParameter(name = "id", type = ParameterIn.PATH) @PathVariable UUID id) {
        return responseHelper.awaitNoContent(service.deleteById(id));
    }

    @ApiMethod(method = RequestMethod.DELETE, path = AmlethMPEndpoint.Song.DISABLE)
    public Mono<ResponseEntity<Void>> disable(
            @ApiParameter(name = "id", type = ParameterIn.PATH) @PathVariable UUID id) {
        return responseHelper.awaitNoContent(service.disable(id));
    }

    @ApiMethod(method = RequestMethod.POST, path = AmlethMPEndpoint.Song.ENABLE)
    public Mono<ResponseEntity<Void>> enable(
            @ApiParameter(name = "id", type = ParameterIn.PATH) @PathVariable UUID id) {
        return responseHelper.awaitNoContent(service.enable(id));
    }
}
