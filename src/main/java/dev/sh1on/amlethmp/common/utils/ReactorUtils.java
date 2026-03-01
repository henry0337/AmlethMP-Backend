package dev.sh1on.amlethmp.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import org.reactivestreams.Publisher;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReactorUtils {

    /**
     * Lấy ra giá trị được wrap trong một {@link Mono} được chỉ định.
     * @deprecated Phương thức này sẽ ép {@link Publisher} được chỉ định chạy đồng bộ trên một luồng nên hệ thống rất có
     * khả năng sẽ bị treo, vì vậy phương thức kiểu này sẽ được đánh dấu là lỗi thời với mục đích là ưu tiên hệ thống
     * bất đồng bộ hoàn toàn.
     * @param publisher Đối tượng {@link Mono} cần lấy giá trị được wrap tương ứng
     * @return Giá trị được wrap bên trong nếu tồn tại, nếu như {@link Mono#empty()} thì trả về {@code null}.
     * @param <T> Kiểu dữ liệu được wrap trong {@link Mono}
     * @author <a href="https://github.com/AdorableDandelion25">Patricia</a>
     */
    @Nullable
    @Deprecated(since = "0.0.1-SNAPSHOT")
    public static <T> T markAsSynchronous(Mono<T> publisher) {
        return publisher.block();
    }

    /**
     * Lấy ra giá trị {@link List} được wrap trong một {@link Flux} được chỉ định.
     * @deprecated Phương thức này sẽ ép {@link Publisher} được chỉ định chạy đồng bộ trên một luồng nên hệ thống rất có
     * khả năng sẽ bị treo, vì vậy phương thức kiểu này sẽ được đánh dấu là lỗi thời với mục đích là ưu tiên hệ thống
     * bất đồng bộ hoàn toàn.
     * @param publisher Đối tượng {@link Flux} cần lấy giá trị được wrap tương ứng
     * @return Danh sách giá trị được wrap bên trong nếu tồn tại, nếu như {@link Flux#empty()} thì trả về {@code null}.
     * @param <T> Kiểu dữ liệu được wrap trong {@link Flux}
     * @author <a href="https://github.com/AdorableDandelion25">Patricia</a>
     */
    @Nullable
    @Deprecated(since = "0.0.1-SNAPSHOT")
    public static <T> List<T> markAsSynchronous(Flux<T> publisher) {
        return publisher.collectList().block();
    }
}
