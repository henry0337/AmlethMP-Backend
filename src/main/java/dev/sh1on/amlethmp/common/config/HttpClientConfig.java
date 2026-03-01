package dev.sh1on.amlethmp.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
public class HttpClientConfig {
    @Bean
    WebClient webClient(WebClient.Builder builder) {
        return builder
                .baseUrl("https://api.example.com")
                .defaultHeader("Accept", "application/json")
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create()
                                .responseTimeout(Duration.ofSeconds(10))
                                .compress(true)
                ))
                .build();
    }
}
