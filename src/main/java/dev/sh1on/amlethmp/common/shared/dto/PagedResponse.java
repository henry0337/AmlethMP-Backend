package dev.sh1on.amlethmp.common.shared.dto;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * <b>[Shared DTO]</b> <br>
 * Cấu trúc phản hồi phân trang gọn nhẹ, thay thế cho việc serialize trực tiếp
 * {@link Page}/{@link org.springframework.data.domain.PageImpl PageImpl} vốn dư thừa
 * và không ổn định giữa các phiên bản Spring Data.
 * <p>
 * Chỉ giữ lại những thuộc tính mà phần lớn client thực sự cần để hiển thị danh sách
 * và điều hướng phân trang.
 * </p>
 *
 * @param content       Danh sách bản ghi của trang hiện tại.
 * @param page          Chỉ số trang hiện tại (bắt đầu từ {@code 0}).
 * @param size          Số lượng bản ghi tối đa trên một trang.
 * @param totalElements Tổng số bản ghi trên toàn bộ tập dữ liệu.
 * @param totalPages    Tổng số trang.
 * @param hasNext       {@code true} nếu còn trang phía sau.
 * @param hasPrevious   {@code true} nếu còn trang phía trước.
 * @param <T>           Kiểu dữ liệu của mỗi bản ghi (thường là Output DTO).
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
public record PagedResponse<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean hasNext,
        boolean hasPrevious
) {

    /**
     * Tạo {@link PagedResponse} từ một {@link Page} của Spring Data.
     *
     * @param source Đối tượng {@link Page} nguồn.
     * @param <T>    Kiểu dữ liệu của mỗi bản ghi.
     * @return Một {@link PagedResponse} tương ứng.
     */
    public static <T> PagedResponse<T> from(Page<T> source) {
        return new PagedResponse<>(
                source.getContent(),
                source.getNumber(),
                source.getSize(),
                source.getTotalElements(),
                source.getTotalPages(),
                source.hasNext(),
                source.hasPrevious()
        );
    }
}
