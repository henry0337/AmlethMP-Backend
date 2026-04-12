package dev.sh1on.amlethmp.common.config;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

/**
 * Bộ lọc (Filter) dùng để đưa {@link ServerWebExchange} hiện tại vào Reactive Context.
 * Điều này cho phép các lớp tiện ích truy cập thông tin request mà không cần truyền qua tham số phương thức.
 *
 * @author <a href="https://github.com/henry0337">S3lena</a>
 */
@Component
public class ServerWebExchangeContextFilter implements WebFilter {

    public static final Class<ServerWebExchange> EXCHANGE_CONTEXT_KEY = ServerWebExchange.class;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        return chain.filter(exchange).contextWrite(Context.of(EXCHANGE_CONTEXT_KEY, exchange));
    }

    public static Mono<ServerWebExchange> getExchange() {
        return Mono.deferContextual(ctx -> Mono.justOrEmpty(ctx.getOrEmpty(EXCHANGE_CONTEXT_KEY)));
    }
}
