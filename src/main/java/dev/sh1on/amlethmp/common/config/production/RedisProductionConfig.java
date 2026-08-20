package dev.sh1on.amlethmp.common.config.production;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import dev.sh1on.amlethmp.common.constant.AppConstant;

/**
 * <b>[Configuration Class]</b> <br>
 * Lớp cấu hình Redis cho môi trường <b>thật (Production)</b>.
 *
 * @author <a href="https://github.com/henry0337">S3lena</a>
 */
@Configuration
@Profile(AppConstant.Environment.PROD)
class RedisProductionConfig { }
