package dev.sh1on.amlethmp.common.template.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * <b>[Internal API, Controller-only]</b> <br>
 * Giao diện nền được tùy chỉnh dành riêng cho hệ thống <b>AmlethMP</b>, giúp lập trình viên khởi tạo các lớp
 * {@linkplain RestController REST API Controller} cho dự án nhanh hơn.
 * <p>
 * Lớp trừu tượng này cung cấp các phương thức CRUD cơ bản (Create, Read, Update, Delete)
 * được thiết kế theo mô hình <b>Reactive Programming</b> của Spring WebFlux, sử dụng
 * {@link org.reactivestreams.Publisher Publisher} để xử lý bất đồng bộ.
 * </p>
 *
 * @param <OD> Output DTO (Dùng để hiển thị thông tin cho phía client)
 * @param <K>  Key (Điều kiện tìm kiếm entity, thường là ID)
 * @param <CD> Create DTO (Dùng để thêm mới dữ liệu)
 * @param <UD> Update DTO (Dùng để cập nhật dữ liệu)
 * @author <a href="https://github.com/henry0337">S3lena</a>
 */
public abstract class AmlethMPRestController<OD, K, CD, UD> extends AmlethMPController {

    /**
     * Lấy danh sách toàn bộ dữ liệu có hỗ trợ phân trang và sắp xếp.
     *
     * @param offset Vị trí bắt đầu (trang)
     * @param limit  Số lượng bản ghi tối đa trên mỗi trang
     * @param order  Thứ tự sắp xếp (asc hoặc desc)
     * @param prop   Tên trường dữ liệu dùng để sắp xếp
     * @return Một {@link Mono} chứa kết quả phân trang {@link Page}
     */
    public abstract Mono<ResponseEntity<Page<OD>>> findAll(Integer offset, Integer limit, String order, String prop);

    /**
     * Tìm kiếm thông tin chi tiết của một bản ghi dựa trên khóa chính hoặc điều kiện xác định.
     *
     * @param key Khóa tìm kiếm
     * @return Một {@link Mono} chứa thông tin DTO của bản ghi nếu tìm thấy
     */
    public abstract Mono<ResponseEntity<OD>> findByKey(K key);

    /**
     * Thêm mới một bản ghi vào hệ thống.
     *
     * @param dto DTO chứa thông tin cần tạo
     * @return Một {@link Mono} chứa thông tin của bản ghi vừa tạo thành công
     */
    public abstract Mono<ResponseEntity<OD>> create(CD dto);

    /**
     * Cập nhật thông tin của một bản ghi hiện có dựa trên khóa xác định.
     *
     * @param key Khóa xác định bản ghi cần cập nhật
     * @param dto DTO chứa thông tin cập nhật mới
     * @return Một {@link Mono} chứa thông tin của bản ghi sau khi đã cập nhật
     */
    public abstract Mono<ResponseEntity<OD>> update(K key, UD dto);

    /**
     * Xóa vĩnh viễn một bản ghi khỏi hệ thống (Hard Delete).
     *
     * @param key Khóa xác định bản ghi cần xóa
     * @return Một {@link Mono} rỗng biểu thị trạng thái hoàn thành
     */
    public abstract Mono<ResponseEntity<Void>> delete(K key);

    /**
     * Vô hiệu hóa hoặc tạm ẩn một bản ghi mà không xóa khỏi cơ sở dữ liệu (Soft Delete).
     *
     * @param key Khóa xác định bản ghi cần vô hiệu hóa
     * @return Một {@link Mono} rỗng biểu thị trạng thái hoàn thành
     */
    public abstract Mono<ResponseEntity<Void>> disable(K key);
}
