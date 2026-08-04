package dev.sh1on.amlethmp.common.config;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.server.i18n.AcceptHeaderLocaleContextResolver;
import org.springframework.web.server.i18n.LocaleContextResolver;

/**
 * <p>Lớp cấu hình chức năng <b>quốc tế hóa (i18n)</b> cho ứng dụng.</p>
 *
 * @see <a href="https://docs.spring.io/spring-boot/reference/features/internationalization.html">Internationalization</a>
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
@Configuration(proxyBeanMethods = false)
class LocalizationConfig {

    @Bean
    MessageSource messageSource() {
        var messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:i18n/messages");
        messageSource.setDefaultEncoding(Charset.defaultCharset().name());
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
