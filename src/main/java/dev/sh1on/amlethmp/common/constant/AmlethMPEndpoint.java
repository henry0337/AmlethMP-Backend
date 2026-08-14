package dev.sh1on.amlethmp.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * <b>[Constant]</b> <br>
 * Nơi tập trung toàn bộ endpoint của hệ thống <b>AmlethMP</b>.
 *
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
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
        public static final String SEGMENT = "/auth";
        public static final String BASE = API_PREFIX + SEGMENT + V1;

        /** Đăng nhập, trả về JWT. */
        public static final String LOGIN = "/login";
        /** Đăng ký tài khoản mới. */
        public static final String REGISTER = "/register";
        /** Đăng xuất, thu hồi JWT đang được sử dụng. */
        public static final String LOGOUT = "/logout";
    }

    /**
     * Nhóm endpoint cho mô-đun <b>người dùng</b> (user).
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class User {
        public static final String SEGMENT = "/user";
        public static final String BASE = API_PREFIX + SEGMENT + V1;
        public static final String BY_ID = "/{id}";
        public static final String DISABLE = "/{id}/disable";
        public static final String ENABLE = "/{id}/enable";
    }

    /**
     * Nhóm endpoint cho mô-đun <b>bài hát</b> (song).
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Song {
        public static final String SEGMENT = "/song";
        public static final String BASE = API_PREFIX + SEGMENT + V1;
        public static final String BY_ID = "/{id}";
        public static final String DISABLE = "/{id}/disable";
        public static final String ENABLE = "/{id}/enable";
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
