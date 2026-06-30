package dev.sh1on.amlethmp.common.shared.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AppConstant {
    public static final String BLANK = "";

    // =====================================================================================
    // OS / SYSTEM
    // -------------------------------------------------------------------------------------
    // Các constant liên quan tới hệ điều hành và shell/command của môi trường chạy.
    // =====================================================================================

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
     * @see <a href="https://man.archlinux.org/man/xdg-open.1.en">xdg-open (Arch Linux)</a>
     */
    public static final String OPEN_LINUX = "/usr/bin/xdg-open";

    // =====================================================================================
    // LOCALIZATION (i18n message codes)
    // -------------------------------------------------------------------------------------
    // Các constant trỏ tới key của message trong src/main/resources/i18n/messages*.properties.
    // Dùng kèm I18NUtils/i18nService.translate(...) thay vì viết string literal trực tiếp.
    // Mỗi nhóm con dưới đây tương ứng với một section trong file messages.properties.
    // =====================================================================================

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class MessageCode {

        // ---- OS -------------------------------------------------------------------------

        /** Hệ điều hành hiện tại không được backend hỗ trợ. */
        public static final String OS_UNSUPPORTED = "os.unsupported";

        // ---- SonarScanner ---------------------------------------------------------------

        /** Đang kiểm tra xem SonarScanner có đang chạy hay không. */
        public static final String SONAR_CHECK = "sonar.check";

        /** SonarScanner đang chạy tại {@code {0}} (URL). */
        public static final String SONAR_RUNNING = "sonar.running";

        /** SonarScanner không chạy. */
        public static final String SONAR_NOT_RUNNING = "sonar.not_running";

        /** Lỗi khi kiểm tra hoặc mở URL của SonarScanner. */
        public static final String SONAR_ERROR = "sonar.error";

        // ---- Browser --------------------------------------------------------------------

        /** Lỗi khi mở trình duyệt cho URL của SonarScanner. */
        public static final String BROWSER_OPEN_ERROR = "browser.open.error";

        // ---- Exceptions -----------------------------------------------------------------

        /** Không tìm thấy người dùng. */
        public static final String ERROR_USER_NOT_FOUND = "error.user.not_found";

        /** Không tìm thấy người dùng theo username {@code {0}}. */
        public static final String ERROR_USER_NOT_FOUND_WITH_USERNAME = "error.user.not_found_with_username";

        /** Thông tin đăng nhập không hợp lệ. */
        public static final String ERROR_AUTH_INVALID_CREDENTIALS = "error.auth.invalid_credentials";

        /** {@code responseHandler} phải là null nếu {@code statusPredicate} là null. */
        public static final String ERROR_REST_CLIENT_HANDLER_REQUIRES_PREDICATE =
                "error.rest_client.handler_requires_predicate";
    }
}
