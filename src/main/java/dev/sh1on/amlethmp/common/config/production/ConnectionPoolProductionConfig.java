package dev.sh1on.amlethmp.common.config.production;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.sh1on.amlethmp.common.constant.AppConstant;
import javax.sql.DataSource;

/**
 * <b>[Configuration Class]</b> <br>
 * Lớp cấu hình <b>DataSource</b> cho môi trường thật (production).
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 */
@Configuration(proxyBeanMethods = false)
@Profile(AppConstant.Environment.PROD)
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
