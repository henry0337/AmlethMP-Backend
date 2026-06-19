package dev.sh1on.amlethmp.user.controller;

import dev.sh1on.amlethmp.AmlethMPEndpoint;
import dev.sh1on.amlethmp.common.shared.dto.PagedResponse;
import dev.sh1on.amlethmp.common.template.controller.AmlethMPRestController;
import dev.sh1on.amlethmp.user.dto.UserCreateDto;
import dev.sh1on.amlethmp.user.dto.UserDto;
import dev.sh1on.amlethmp.user.dto.UserUpdateDto;
import dev.sh1on.amlethmp.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Lớp tiếp nhận các <b>request HTTP</b> liên quan tới mô-đun {@link dev.sh1on.amlethmp.user.model.User User}.
 *
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@RestController
@RequestMapping(AmlethMPEndpoint.User.BASE)
@RequiredArgsConstructor
@Tag(name = "User", description = "Mô-đun xử lý thông tin liên quan tới người dùng")
public class UserController extends AmlethMPRestController<UserDto, UUID, UserCreateDto, UserUpdateDto> {
    private final UserService service;

    /**
     * Phương thức đại diện request <b>GET</b> dùng để lấy ra toàn bộ bản ghi đọc được từ cơ sở dữ liệu.
     *
     * @param offset Số lượng bản ghi muốn bỏ qua, mặc định là {@code 0}.
     * @param limit  Số lượng bản ghi tối đa có thể hiển thị trong 1 trang, mặc định là {@code 10}.
     * @param order  Chỉ định hướng sắp xếp của danh sách sẽ được hiển thị, giá trị hợp lệ chỉ có thể là {@code ASC} hoặc
     *               {@code DESC}, mặc định là {@code ASC}.
     * @param prop   Tên thuộc tính tương ứng với cột trong bảng thuộc cơ sở dữ liệu được chỉ định để làm đối tượng được
     *               sắp xếp, mặc định là rỗng ({@code ""}).
     * @return Đối tượng {@link PagedResponse} trả về danh sách bản ghi của trang hiện tại kèm thông tin điều hướng phân
     * trang.
     */
    @Override
    @GetMapping
    public Mono<ResponseEntity<PagedResponse<UserDto>>> findAll(@RequestParam(defaultValue = "0") Integer offset,
                                                                @RequestParam(defaultValue = "10") Integer limit,
                                                                @RequestParam(defaultValue = "ASC") String order,
                                                                @RequestParam(defaultValue = "") String prop) {
        var sort = Sort.unsorted();
        if (!order.isBlank() && !prop.isBlank()) {
            sort = Sort.by(Sort.Direction.fromString(order), prop);
        }

        var pageRequest = PageRequest.of(offset, limit, sort);
        return controllerUtils.awaitPaged(service.findAll(pageRequest));
    }

    /**
     * Phương thức đại diện request <b>GET</b> dùng để lấy ra bản ghi nếu tìm được thông qua {@code id} từ cơ sở dữ liệu.
     *
     * @param id ID được sử dụng để tìm kiếm bản ghi.
     * @return Thông tin người dùng tương ứng với ID đó (nếu có).
     */
    @Override
    @GetMapping(AmlethMPEndpoint.User.BY_ID)
    public Mono<ResponseEntity<UserDto>> findByKey(@PathVariable UUID id) {
        return controllerUtils.awaitOrNotFound(service.findByKey(id));
    }

    /**
     * Phương thức đại diện request <b>POST</b> dùng để tạo mới một bản ghi người dùng trong cơ sở dữ liệu.
     *
     * @param dto Đối tượng {@link UserCreateDto} chứa thông tin cần thiết để tạo người dùng mới.
     * @return Đối tượng {@link ResponseEntity} chứa {@link UserDto} vừa được tạo.
     */
    @Override
    @PostMapping
    public Mono<ResponseEntity<UserDto>> create(@RequestBody UserCreateDto dto) {
        return controllerUtils.awaitCreated(service.save(dto));
    }

    /**
     * Phương thức đại diện request <b>PUT</b> dùng để cập nhật thông tin bản ghi người dùng theo {@code id}.
     *
     * @param id  ID của người dùng cần cập nhật.
     * @param dto Đối tượng {@link UserUpdateDto} chứa các thông tin muốn thay đổi.
     * @return Đối tượng {@link ResponseEntity} chứa {@link UserDto} sau khi cập nhật.
     */
    @Override
    @PutMapping(AmlethMPEndpoint.User.BY_ID)
    public Mono<ResponseEntity<UserDto>> update(@PathVariable UUID id, @RequestBody UserUpdateDto dto) {
        return controllerUtils.awaitOk(service.update(id, dto));
    }

    /**
     * Phương thức đại diện request <b>DELETE</b> dùng để xóa bản ghi người dùng theo {@code id}.
     *
     * @param id ID của người dùng cần xóa.
     * @return Đối tượng {@link ResponseEntity} trống khi xóa thành công.
     */
    @Override
    @DeleteMapping(AmlethMPEndpoint.User.BY_ID)
    public Mono<ResponseEntity<Void>> delete(@PathVariable UUID id) {
        return controllerUtils.awaitNoContent(service.deleteById(id));
    }

    @Override
    @DeleteMapping(AmlethMPEndpoint.User.DISABLE)
    public Mono<ResponseEntity<Void>> disable(@PathVariable UUID id) {
        return controllerUtils.awaitNoContent(service.disableById(id));
    }

    /**
     * Phương thức đại diện request <b>POST</b> dùng để kích hoạt lại bản ghi người dùng đã bị vô hiệu hóa theo {@code id}.
     *
     * @param id ID của người dùng cần kích hoạt lại.
     * @return Đối tượng {@link ResponseEntity} trống khi kích hoạt lại thành công.
     */
    @Override
    @PostMapping(AmlethMPEndpoint.User.ENABLE)
    public Mono<ResponseEntity<Void>> enable(@PathVariable UUID id) {
        return controllerUtils.awaitOkEmpty(service.enableById(id));
    }
}
