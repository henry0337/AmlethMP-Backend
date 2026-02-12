package dev.sh1on.amlethmp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

/**
 * @author <a href="https://github.com/AdorableDandelion25">Patricia</a>, <a href="https://github.com/henry0337">Amleth</a>
 */
@SpringBootApplication
@EnableR2dbcAuditing
public class AmlethMPBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(AmlethMPBackendApplication.class, args);
	}
}
