package dev.sh1on.amlethmp;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Tập trung toàn bộ hằng số đường dẫn (endpoint) của hệ thống <b>AmlethMP</b>.
 * <p>
 * Mỗi mô-đun được gom thành một nhóm riêng (nested class), cung cấp đường dẫn gốc ({@code BASE})
 * và các đường dẫn con tương ứng. Các controller tham chiếu tới các hằng này thay vì viết chuỗi
 * literal trực tiếp.
 * </p>
 *
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 * @author <a href="https://github.com/henry0337">S3lena</a>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AmlethMPEndpoint {

    /** Tiền tố chung cho toàn bộ API. */
    public static final String API_PREFIX = "/api";

    /** Phiên bản API hiện hành. */
    public static final String V1 = "/v1";

    /**
     * Nhóm endpoint cho mô-đun <b>xác thực</b> (auth).
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Auth {
        /** Phân đoạn định danh mô-đun. */
        public static final String SEGMENT = "/auth";
        /** Đường dẫn gốc: {@code /api/auth/v1}. */
        public static final String BASE = API_PREFIX + SEGMENT + V1;

        /** Đăng nhập, trả về JWT. */
        public static final String LOGIN = "/login";
        /** Đăng ký tài khoản mới. */
        public static final String REGISTER = "/register";
    }

    /**
     * Nhóm endpoint cho mô-đun <b>người dùng</b> (user).
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class User {
        /** Phân đoạn định danh mô-đun. */
        public static final String SEGMENT = "/user";
        /** Đường dẫn gốc: {@code /api/user/v1}. */
        public static final String BASE = API_PREFIX + SEGMENT + V1;

        /** Thao tác trên một bản ghi theo id (xem chi tiết / cập nhật / xóa cứng). */
        public static final String BY_ID = "/{id}";
        /** Vô hiệu hóa (soft-delete) một bản ghi. */
        public static final String DISABLE = "/{id}/disable";
        /** Kích hoạt lại một bản ghi đã bị vô hiệu hóa. */
        public static final String ENABLE = "/{id}/enable";
    }

    /**
     * Nhóm endpoint cho mô-đun <b>bài hát</b> (song).
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Song {
        /** Phân đoạn định danh mô-đun. */
        public static final String SEGMENT = "/song";
        /** Đường dẫn gốc: {@code /api/song/v1}. */
        public static final String BASE = API_PREFIX + SEGMENT + V1;
    }

    /**
     * Nhóm đường dẫn của <b>tài liệu API</b> (springdoc / swagger-ui).
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Docs {
        /** Đặc tả OpenAPI dạng JSON. */
        public static final String API_DOCS = "/v3/api-docs/**";
        /** Tài nguyên tĩnh của Swagger UI. */
        public static final String SWAGGER_UI = "/swagger-ui/**";
        /** Trang Swagger UI. */
        public static final String SWAGGER_UI_HTML = "/swagger-ui.html";
    }
}
