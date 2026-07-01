package dev.sh1on.amlethmp.common.config.development;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.sh1on.amlethmp.common.shared.constant.AppConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

/**
 * <p>Lớp cấu hình {@link DataSource} cho môi trường <b>phát triển (development)</b>.</p>
 *
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Configuration(proxyBeanMethods = false)
@Profile(AppConstant.Environment.DEV)
class ConnectionPoolDevelopmentConfig {
    @Value("${spring.liquibase.url}")
    private String url;

    @Value("${spring.liquibase.user}")
    private String username;

    @Value("${spring.liquibase.password}")
    private String password;

    @Bean
    DataSource dataSource() {
        var config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);

        return new HikariDataSource(config);
    }
}
