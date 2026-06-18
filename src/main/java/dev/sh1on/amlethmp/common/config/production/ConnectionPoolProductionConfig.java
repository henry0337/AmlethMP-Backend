package dev.sh1on.amlethmp.common.config.production;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

/**
 * <p>Lớp cấu hình <b>DataSource</b> cho môi trường triển khai (production).</p>
 *
 * @author <a href="https://github.com/henry0337">S3lena</a>
 */
@Profile("prod")
@Configuration(proxyBeanMethods = false)
class ConnectionPoolProductionConfig {
    @Value("${spring.liquibase.url}")
    private String url;

    @Value("${spring.liquibase.user}")
    private String username;

    @Value("${spring.liquibase.password}")
    private String password;

    @Bean
    DataSource productionDataSource() {
        var config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);

        return new HikariDataSource(config);
    }
}
