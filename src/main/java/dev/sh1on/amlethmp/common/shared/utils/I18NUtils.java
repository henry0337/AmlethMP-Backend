package dev.sh1on.amlethmp.common.shared.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import java.util.Locale;

import static dev.sh1on.amlethmp.common.shared.constant.AppConstant.BLANK;

/**
 * Lớp tiện ích hỗ trợ việc trích xuất và xử lý các thông báo đa ngôn ngữ (i18n)
 * một cách an toàn thông qua {@link MessageSource}.
 *
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class I18NUtils {
    private final MessageSource messageSource;

    /**
     * Trích xuất một thông báo tĩnh (không chứa tham số động) dựa trên mã code.
     *
     * @param code mã thông báo (message code) được định nghĩa trong các tệp properties
     * @return nội dung thông báo đã được bản địa hóa, hoặc một chuỗi rỗng ({@link dev.sh1on.amlethmp.common.shared.constant.AppConstant#BLANK}) nếu không tìm thấy mã
     */
    public String translateMessage(String code) {
        try {
            return messageSource.getMessage(code, null, Locale.getDefault());
        } catch (NoSuchMessageException e) {
            log.warn(e.getLocalizedMessage());
            return BLANK;
        }
    }

    /**
     * Trích xuất một thông báo tĩnh (không chứa tham số động) dựa trên mã code và ngôn ngữ được chỉ định.
     *
     * @param code   mã thông báo (message code) được định nghĩa trong các tệp properties
     * @param locale đối tượng {@link Locale} chỉ định ngôn ngữ mong muốn.
     * @return nội dung thông báo đã được bản địa hóa, hoặc một chuỗi rỗng nếu không tìm thấy mã
     */
    public String translateMessage(String code, Locale locale) {
        try {
            return messageSource.getMessage(code, null, locale);
        } catch (NoSuchMessageException e) {
            log.warn(e.getLocalizedMessage());
            return BLANK;
        }
    }

    /**
     * Trích xuất một thông báo động (có chứa tham số để thay thế các placeholder {0}, {1}...) dựa trên mã code.
     * Sử dụng ngôn ngữ mặc định của hệ thống.
     *
     * @param code mã thông báo (message code)
     * @param args danh sách các tham số động dùng để truyền vào và định dạng chuỗi thông báo
     * @return nội dung thông báo đã được bản địa hóa và thay thế tham số, hoặc một chuỗi rỗng nếu không tìm thấy mã
     */
    public String translateDynamicMessage(String code, Object... args) {
        try {
            return messageSource.getMessage(code, args, Locale.getDefault());
        } catch (NoSuchMessageException e) {
            log.warn(e.getLocalizedMessage());
            return BLANK;
        }
    }

    /**
     * Trích xuất một thông báo động (có chứa tham số truyền vào) dựa trên mã code và ngôn ngữ được chỉ định.
     *
     * @param code   mã thông báo (message code)
     * @param args   mảng các tham số động dùng để định dạng chuỗi (các tham số bên trong có thể là {@code null})
     * @param locale đối tượng {@link Locale} chỉ định ngôn ngữ mong muốn (có thể là {@code null})
     * @return Nội dung thông báo đã được bản địa hóa và thay thế tham số, hoặc một chuỗi rỗng nếu không tìm thấy mã
     */
    public String translateDynamicMessage(String code, Object[] args, Locale locale) {
        try {
            return messageSource.getMessage(code, args, locale);
        } catch (NoSuchMessageException e) {
            log.warn(e.getLocalizedMessage());
            return BLANK;
        }
    }
}
