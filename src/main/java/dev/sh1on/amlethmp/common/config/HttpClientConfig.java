package dev.sh1on.amlethmp.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.List;

@Configuration
public class HttpClientConfig {
    @Bean
    WebClient webClient(WebClient.Builder builder) {
        return builder
                .defaultHeaders(httpHeaders -> {
                    var headers = new HttpHeaders();
                    headers.add(HttpHeaders.ACCEPT, "*/*");
                    headers.addAll(HttpHeaders.ACCEPT_LANGUAGE, List.of("vi-VN", "en-US", "ja-JP"));
                    headers.addAll(HttpHeaders.ACCEPT_ENCODING, List.of("gzip, deflate"));
                    headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

                    httpHeaders.addAll(headers);
                })
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create()
                                .responseTimeout(Duration.ofSeconds(10))
                                .compress(true)
                ))
                .build();
    }
}
