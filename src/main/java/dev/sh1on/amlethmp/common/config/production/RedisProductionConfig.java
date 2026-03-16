package dev.sh1on.amlethmp.common.config.production;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Lớp cấu hình Redis dành riêng cho môi trường <b>Production (prod)</b>.
 *
 * @author <a href="https://github.com/henry0337">S3lena</a>
 */
@Configuration
@Profile("prod")
public class RedisProductionConfig { }
