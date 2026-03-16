package dev.sh1on.amlethmp.common.shared.service;

import dev.sh1on.amlethmp.common.shared.utils.MessageUtils;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Một <b>REST client</b> được tùy biến bằng {@link WebClient} của Spring Boot sử dụng trong môi trường <b>Reactive</b> để thực
 * hiện các tác vụ gửi yêu cầu HTTP.
 *
 * @author <a href="https://github.com/AdorableDandelion25">Patricia</a>
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RestClient {
    private final WebClient webClient;
    private final MessageUtils messageUtils;

    /**
     * Thực hiện gửi một yêu cầu HTTP GET đến URI đã chỉ định và "unwrap" (giải nén) body phản hồi
     * thành kiểu dữ liệu được chỉ định.
     *
     * @param uri             URI đích để gửi yêu cầu tới
     * @param responseType    Kiểu dữ liệu đích mong muốn sẽ phản hồi về
     * @param params          Các tham số truy vấn (query parameters) sẽ được thêm vào URI
     * @param headers         Các header (ngoài các header mặc định) sẽ được thêm vào yêu cầu
     * @param statusPredicate Lambda giúp kiểm tra các mã phản hồi đích sẽ được trả về
     *                        (một phần của {@link WebClient.ResponseSpec#onStatus(Predicate, Function) onStatus(Predicate, Function)})
     * @param responseHandler Lambda xử lý dựa trên mã phản hồi đích được trả về giúp tùy biến kết quả phản hồi nếu có mã
     *                        lỗi xảy ra (một phần của {@link WebClient.ResponseSpec#onStatus(Predicate, Function) onStatus(Predicate, Function)})
     * @param <T>             Kiểu dữ liệu mà body phản hồi sẽ được "unwrap" thành.
     * @return Một phản hồi đã được "unwrap" có kiểu {@code T}.
     * @throws IllegalArgumentException nếu {@code statusPredicate == null} nhưng {@code responseHandler != null}.
     */
    @Retry(name = "unwrapGet", fallbackMethod = "retryFallback")
    @CircuitBreaker(name = "unwrapGet", fallbackMethod = "circuitBreakerFallback")
    public <T> Mono<T> doGet(String uri,
                             ParameterizedTypeReference<T> responseType,
                             @Nullable Map<String, String> params,
                             @Nullable Map<String, ?> headers,
                             @Nullable Predicate<HttpStatusCode> statusPredicate,
                             @Nullable Function<ClientResponse, Mono<? extends Throwable>> responseHandler) {
        WebClient.ResponseSpec responseSpec = webClient.get()
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
                .retrieve();

        if (statusPredicate == null) {
            if (responseHandler != null) {
                throw new IllegalArgumentException("Tham số \"responseHandler\" phải là null nếu như \"statusPredicate\" null!");
            }

            return responseSpec.bodyToMono(responseType);
        }

        if (responseHandler != null) {
            return responseSpec.onStatus(statusPredicate, responseHandler).bodyToMono(responseType);
        }

        return responseSpec.bodyToMono(responseType);
    }

    /**
     * Thực hiện gửi một yêu cầu HTTP POST đến URI đã chỉ định và "unwrap" (giải nén) body phản hồi
     * thành kiểu dữ liệu được chỉ định.
     *
     * @param uri             URI để gửi yêu cầu POST.
     * @param body            Đối tượng body sẽ được gửi trong yêu cầu POST.
     * @param responseType    Một {@link ParameterizedTypeReference} đại diện cho kiểu dữ liệu mong đợi của body phản hồi.
     * @param headers         Một {@link Map} tùy chọn chứa các tiêu đề (headers) sẽ được thêm vào yêu cầu.
     * @param statusPredicate Một {@link Predicate} tùy chọn để xác định liệu một {@link HttpStatusCode} có nên được xử lý hay không.
     *                        Nếu là {@code null}, chỉ các trạng thái 2xx thành công mới được xem xét.
     * @param responseHandler Một {@link Function} tùy chọn để xử lý {@link ClientResponse} khi trạng thái
     *                        khớp với {@code statusPredicate}. Nếu {@code statusPredicate} không phải là {@code null},
     *                        thì tham số này cũng không được là {@code null}.
     * @param <I>             Kiểu dữ dữ liệu của đối tượng body đầu vào.
     * @param <O>             Kiểu dữ liệu mà body phản hồi sẽ được "unwrap" thành.
     * @return Một {@link Mono} phát ra body phản hồi đã được "unwrap" có kiểu {@code O}.
     */
    @Retry(name = "unwrapPost", fallbackMethod = "retryFallback")
    @CircuitBreaker(name = "unwrapPost", fallbackMethod = "circuitBreakerFallback")
    public <I, O> Mono<O> doPost(String uri,
                                 I body,
                                 ParameterizedTypeReference<O> responseType,
                                 @Nullable Map<String, ?> headers,
                                 @Nullable Predicate<HttpStatusCode> statusPredicate,
                                 @Nullable Function<ClientResponse, Mono<? extends Throwable>> responseHandler) {
        WebClient.ResponseSpec responseSpec = webClient.post()
                .uri(uri)
                .headers(httpHeaders -> {
                    if (headers != null) {
                        headers.forEach((k, v) -> httpHeaders.add(k, String.valueOf(v)));
                    }
                })
                .bodyValue(body)
                .retrieve();

        if (statusPredicate == null) {
            if (responseHandler != null) {
                throw new IllegalArgumentException("Tham số \"responseHandler\" phải là null nếu như \"statusPredicate\" null!");
            }

            return responseSpec.bodyToMono(responseType);
        }

        if (responseHandler != null) {
            return responseSpec.onStatus(statusPredicate, responseHandler).bodyToMono(responseType);
        }

        return responseSpec.bodyToMono(responseType);
    }

    /**
     * Phương thức dự phòng sẽ được gọi ra khi các phương thức HTTP được truy vấn gặp lỗi.
     *
     * @return Một thông báo dự phòng được bản địa hóa thu được từ {@link MessageUtils}.
     */
    @SuppressWarnings("unused")
    private String retryFallback() {
        return messageUtils.obtainStaticLocalizedMessage("httpClient.fallback");
    }

    @SuppressWarnings("unused")
    private String circuitBreakerFallback() {
        return messageUtils.obtainStaticLocalizedMessage("httpClient.fallback");
    }
}
