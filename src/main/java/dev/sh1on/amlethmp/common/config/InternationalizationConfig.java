package dev.sh1on.amlethmp.common.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.server.i18n.AcceptHeaderLocaleContextResolver;
import org.springframework.web.server.i18n.LocaleContextResolver;

import java.util.List;
import java.util.Locale;

/**
 * <p>Lớp cấu hình chức năng <b>quốc tế hóa (i18n)</b> cho ứng dụng.</p>
 * <p>Quản lý việc thay đổi ngôn ngữ dựa trên <b>Accept-Language</b> từ phía người dùng.</p>
 *
 * @see <a href="https://docs.spring.io/spring-boot/reference/features/internationalization.html">Internationalization</a>
 * @author <a href="https://github.com/AdorableDandelion25">Patricia</a>
 */
@Configuration
public class InternationalizationConfig {

    @Bean
    MessageSource messageSource() {
        var messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    LocaleContextResolver lcr() {
        var localeResolver = new AcceptHeaderLocaleContextResolver();
        localeResolver.setDefaultLocale(Locale.of("vi", "VN"));
        localeResolver.setSupportedLocales(List.of(Locale.ENGLISH, Locale.JAPANESE, Locale.of("vi", "VN")));
        return localeResolver;
    }
}
