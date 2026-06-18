package dev.sh1on.amlethmp.common.shared.utils;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author <a href="https://github.com/henry0337">S3lena</a>
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Component
public class ReactorUtils {

    /**
     * Tạo một {@link Mono} mới phát ra một {@code data} được chỉ định, dữ liệu đó sẽ được thu thập vào thời điểm
     * khởi tạo.
     *
     * @param data Dữ liệu sẽ được phát ra
     * @param <T>  Kiểu dữ liệu của dữ liệu đầu vào
     * @see Mono#just(T)
     */
    public <T> Mono<T> single(T data) {
        return Mono.just(data);
    }

    /**
     * Tạo một {@link Mono} mới phát ra một {@code instance} được chỉ định nếu nó không {@code null}, ngược lại sẽ phát
     * ra {@link Mono#empty()}.
     *
     * @param instance Dữ liệu sẽ được phát ra
     * @param <T>      Kiểu dữ liệu của dữ liệu đầu vào
     * @author <a href="https://github.com/henry0337">S3lena</a>
     * @see Mono#justOrEmpty(T)
     */
    public <T> Mono<T> singleOrEmpty(@Nullable T instance) {
        return Mono.justOrEmpty(instance);
    }

    /**
     * Tạo một {@link Mono} mà không phát ra bất cứ dữ liệu nào cả.
     *
     * @param <T> Kiểu dữ liệu của dữ liệu đầu vào
     * @author <a href="https://github.com/henry0337">S3lena</a>
     * @see Mono#empty()
     */
    public <T> Mono<T> emptyMono() {
        return Mono.empty();
    }

    /**
     * Tạo một {@link Flux} mà không phát ra bất cứ dữ liệu nào cả.
     *
     * @param <T> Kiểu dữ liệu của dữ liệu đầu vào
     * @author <a href="https://github.com/henry0337">S3lena</a>
     * @see Flux#empty()
     */
    public <T> Flux<T> emptyFlux() {
        return Flux.empty();
    }

    /**
     * Ném ra một exception thông qua {@code errorSupplier} được chỉ định nếu {@code source} không chứa dữ liệu.
     *
     * @param source        Đối tượng {@link Mono} cần kiểm tra.
     * @param errorSupplier Hàm cung cấp exception sẽ được trả ra nếu như {@code source} {@linkplain Mono#empty() không có dữ liệu}.
     * @param <T>           Kiểu dữ liệu được bọc trong {@link Mono}.
     * @return {@link Mono} chứa dữ liệu ban đầu hoặc lỗi tương ứng.
     * @author <a href="https://github.com/henry0337">S3lena</a>
     */
    public <T> Mono<T> errorIfEmpty(Mono<T> source, Supplier<? extends Throwable> errorSupplier) {
        return source.switchIfEmpty(Mono.error(errorSupplier));
    }

    /**
     * Kiểm tra tính hợp lệ của dữ liệu bên trong {@link Mono} bằng một {@code predicate} được chỉ định, nếu không
     * thỏa mãn điều kiện sẽ ném ra exception được cung cấp theo {@code errorSupplier}.
     *
     * @param source        Đối tượng {@link Mono} chứa dữ liệu cần kiểm tra.
     * @param predicate     Điều kiện kiểm tra dữ liệu.
     * @param errorSupplier HHàm cung cấp exception sẽ được trả ra nếu như {@code source} không thỏa mãn điều kiện.
     * @param <T>           Kiểu dữ liệu được bọc trong {@link Mono}.
     * @return {@link Mono} chứa dữ liệu nếu hợp lệ, ngược lại chứa lỗi.
     * @author <a href="https://github.com/henry0337">S3lena</a>
     */
    public <T> Mono<T> ensure(Mono<T> source,
                              Predicate<T> predicate,
                              Supplier<? extends Throwable> errorSupplier) {
        return source.flatMap(value -> predicate.test(value) ? single(value) : Mono.error(errorSupplier));
    }

    /**
     * Thực hiện phân nhánh dữ liệu đầu ra dựa trên một {@code condition} được chỉ định.
     *
     * @param condition         Điều kiện logic để quyết định luồng thực thi.
     * @param thenSupplier      Hàm cung cấp luồng dữ liệu nếu điều kiện là {@code true}.
     * @param otherwiseSupplier Hàm cung cấp luồng dữ liệu nếu điều kiện là {@code false}.
     * @param <T>               Kiểu dữ liệu trả về.
     * @return {@link Mono} kết quả từ luồng được chọn.
     * @author <a href="https://github.com/henry0337">S3lena</a>
     */
    public <T> Mono<T> when(boolean condition,
                            Supplier<? extends Mono<T>> thenSupplier,
                            Supplier<? extends Mono<T>> otherwiseSupplier) {
        return condition ? Mono.defer(thenSupplier) : Mono.defer(otherwiseSupplier);
    }

    /**
     * Lấy ra giá trị được bọc trong một {@link Mono} được chỉ định.
     *
     * @param publisher Đối tượng {@link Mono} cần lấy giá trị được wrap tương ứng
     * @param <T>       Kiểu dữ liệu được wrap trong {@link Mono}
     * @return Giá trị được wrap bên trong nếu tồn tại, nếu như {@link Mono#empty()} thì trả về {@code null}.
     * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
     */
    public <T> @Nullable T awaitMono(Mono<T> publisher) {
        return publisher.block();
    }

    /**
     * Lấy ra giá trị {@link List} được bọc trong một {@link Flux} được chỉ định.
     *
     * @param publisher Đối tượng {@link Flux} cần lấy giá trị được wrap tương ứng
     * @param <T>       Kiểu dữ liệu được wrap trong {@link Flux}
     * @return Danh sách giá trị được wrap bên trong nếu tồn tại, nếu như {@link Flux#empty()} thì trả về {@code null}.
     *
     */
    public <T> @Nullable List<T> awaitFluxToList(Flux<T> publisher) {
        return publisher.collectList().block();
    }

    /**
     *
     * @param publisher1
     * @param publisher2
     * @return
     * @param <A>
     * @param <B>
     */
    public <A, B> Mono<Tuple2<A, B>> group(Mono<A> publisher1, Mono<B> publisher2) {
        return publisher1.zipWith(publisher2);
    }
}
