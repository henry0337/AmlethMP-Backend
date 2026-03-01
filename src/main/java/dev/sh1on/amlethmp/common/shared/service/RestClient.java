package dev.sh1on.amlethmp.common.shared.service;

import dev.sh1on.amlethmp.common.utils.MessageUtils;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * Một client REST sử dụng trong môi trường <b>Reactive</b> để thực hiện các yêu cầu HTTP sử dụng {@link WebClient} của
 * Spring.
 *
 * @author <a href="https://github.com/AdorableDandelion25">Patricia</a>
 */
@Component
@RequiredArgsConstructor
public class RestClient {
    private final WebClient webClient;
    private final MessageUtils messageUtils;

    /**
     * Thực hiện yêu cầu HTTP GET tới URI được chỉ định với các tham số truy vấn và tiêu đề tùy chọn.
     * Phương thức này được bọc bởi các chú thích {@link Retry} và {@link CircuitBreaker} của Resilience4j
     * để chịu lỗi.
     *
     * @param uri URI để gửi yêu cầu GET
     * @param params Một bản đồ tùy chọn các tham số truy vấn sẽ được thêm vào URI.
     * @param headers Một bản đồ tùy chọn các tiêu đề sẽ được bao gồm trong yêu cầu.
     * @param responseType Lớp của thân phản hồi dự kiến
     * @param <T> Kiểu của body được dự đoán
     * @return Kết quả phản hồi của phương thức.
     */
    @Retry(name = "externalGet")
    @CircuitBreaker(name = "externalGet", fallbackMethod = "fallback")
    public <T> Mono<T> executeGET(String uri,
                                  @Nullable Map<String, String> params,
                                  @Nullable Map<String, ?> headers,
                                  Class<T> responseType) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(uri)
                        .queryParams(params != null
                                ? MultiValueMap.fromSingleValue(params)
                                : MultiValueMap.fromSingleValue(new HashMap<>()))
                        .build())
                .headers(httpHeaders -> {
                    if (headers != null) {
                        headers.forEach((k, v) -> httpHeaders.add(k, String.valueOf(v)));
                    }
                })
                .retrieve()
                .bodyToMono(responseType);
    }

    /**
     * Thực hiện yêu cầu HTTP POST tới URI được chỉ định với thân yêu cầu và các tiêu đề tùy chọn.
     *
     * @param uri URI để gửi yêu cầu POST.
     * @param body Thân yêu cầu sẽ được gửi. Đối tượng này sẽ được WebClient's message converters tuần tự hóa.
     * @param headers Một bản đồ tùy chọn các tiêu đề sẽ được bao gồm trong yêu cầu.
     *                Các khóa là tên tiêu đề, và các giá trị là biểu diễn đối tượng của chúng (sẽ được chuyển đổi thành chuỗi).
     * @param responseType Lớp của thân phản hồi dự kiến.
     * @param <I> Kiểu của thân yêu cầu.
     * @param <O> Kiểu của thân phản hồi dự kiến.
     * @return Một {@link Mono} phát ra thân phản hồi đã được giải mã kiểu {@code O}.
     */
    @Retry(name = "externalPost")
    @CircuitBreaker(name = "externalPost", fallbackMethod = "fallback")
    public <I, O> Mono<O> executePOST(String uri,
                                      I body,
                                      @Nullable Map<String, ?> headers,
                                      Class<O> responseType) {
        return webClient.post()
                .uri(uri)
                .headers(httpHeaders -> {
                    if (headers != null) {
                        headers.forEach((k, v) -> httpHeaders.add(k, String.valueOf(v)));
                    }
                })
                .bodyValue(body)
                .retrieve()
                .bodyToMono(responseType);
    }

    /**
     * Phương thức dự phòng sẽ được gọi ra khi các phương thức HTTP được truy vấn gặp lỗi.
     *
     * @return Một thông báo dự phòng được bản địa hóa thu được từ {@link MessageUtils}.
     */
    private String fallback() {
        return messageUtils.obtainStaticLocalizedMessage("httpClient.fallback");
    }
}
