package dev.sh1on.amlethmp.common.config.development;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import dev.sh1on.amlethmp.common.shared.constant.AppConstant;

/**
 * <b>[Configuration Class]</b> <br>
 * Lớp cấu hình cho <b>Redis</b>.
 *
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Configuration(proxyBeanMethods = false)
@Profile(AppConstant.Environment.DEV)
class RedisDevelopmentConfig {
    @Bean
    @Primary
    ReactiveRedisTemplate<String, String> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        return new ReactiveRedisTemplate<>(factory, RedisSerializationContext.string());
    }
}
