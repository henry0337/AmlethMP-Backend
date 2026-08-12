package dev.sh1on.amlethmp.common.shared.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AppConstant {
    public static final String BLANK = "";

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Environment {
        public static final String DEV = "dev";
        public static final String PROD = "prod";
    }

    /**
     * Tên biến môi trường gốc: {@code %COMSPEC%} - <b>Command Specifier</b>
     * <p>
     * Biến môi trường trỏ tới đường dẫn tuyệt đối chỉ đến trình thông dịch dòng lệnh mặc định của của hệ điều hành
     * <b>Windows</b>.
     */
    public static final String COM_SPEC = "ComSpec";

    /**
     * Constant đại diện cho lệnh {@code open} ở trên hệ điều hành <b>macOS</b>.
     */
    public static final String OPEN_MACOS = "/usr/bin/open";

    /**
     * Constant đại diện cho lệnh {@code xdg-open} ở trên các bản phân phối của <b>Linux</b>.
     *
     * @see <a href="https://man.archlinux.org/man/xdg-open.1.en">xdg-open (Arch Linux)</a>
     */
    public static final String OPEN_LINUX = "/usr/bin/xdg-open";

    /**
     * Tên System property đánh dấu Swagger UI đã được tự mở trên trình duyệt trong phiên JVM hiện tại.
     */
    public static final String SWAGGER_UI_OPENED_PROPERTY = "amlethmp.devtools.swagger-ui-opened";
}
