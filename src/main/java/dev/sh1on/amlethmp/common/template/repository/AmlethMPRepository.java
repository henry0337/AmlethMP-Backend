package dev.sh1on.amlethmp.common.template.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.NoRepositoryBean;
import reactor.core.publisher.Flux;

/**
 * <b>[Domain Repository's Specific]</b> <br>
 * Repository đặc thù chứa các phương thức truy vấn có thể được sử dụng nhiều trong dự án <b>AmlethMP</b>.
 *
 * @param <T> Kiểu domain mà repository này sẽ quản lý
 * @param <ID> Kiểu của ID thuộc domain mà repository này sẽ quản lý
 * @author <a href="https://github.com/henry0337">S3lena</a>
 */
@NoRepositoryBean
public interface AmlethMPRepository<T, ID> extends R2dbcRepository<T, ID> {
    /**
     * Tìm kiếm toàn bộ các bản ghi có hỗ trợ phân trang.
     *
     * @param pageable Đối tượng chứa thông tin phân trang và sắp xếp
     * @return Một {@link Flux} chứa danh sách các thực thể tìm thấy
     */
    Flux<T> findAll(Pageable pageable);
}
