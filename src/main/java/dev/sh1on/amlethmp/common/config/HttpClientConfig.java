package dev.sh1on.amlethmp.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

/**
 * <p>Lớp cấu hình cho <b>WebClient</b>.</p>
 * <p>Cấu hình các tiêu đề mặc định cho các yêu cầu HTTP.</p>
 */
@Configuration(proxyBeanMethods = false)
class HttpClientConfig {
    @Bean
    WebClient webClient(WebClient.Builder builder) {
        return builder
                .defaultHeaders((HttpHeaders httpHeaders) -> {
                    var headers = new HttpHeaders();
                    headers.add(HttpHeaders.ACCEPT, "*/*");
                    headers.addAll(HttpHeaders.ACCEPT_LANGUAGE, List.of("vi-VN", "en-US", "ja-JP"));
                    headers.addAll(HttpHeaders.ACCEPT_ENCODING, List.of("gzip, deflate"));
                    headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

                    httpHeaders.addAll(headers);
                })
                .build();
    }
}
