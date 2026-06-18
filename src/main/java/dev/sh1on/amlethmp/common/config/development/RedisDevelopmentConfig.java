package dev.sh1on.amlethmp.common.config.development;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

/**
 * <p>Lớp cấu hình cho <b>Redis</b> trong môi trường phát triển (development).</p>
 * <p>Cung cấp cấu hình cho các thao tác với dữ liệu trên <b>Redis</b> thông qua Reactive API.</p>
 *
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Configuration(proxyBeanMethods = false)
@Profile("dev")
class RedisDevelopmentConfig {
    @Bean
    @Primary
    ReactiveRedisTemplate<String, String> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        return new ReactiveRedisTemplate<>(factory, RedisSerializationContext.string());
    }
}
