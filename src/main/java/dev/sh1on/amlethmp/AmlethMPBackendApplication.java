package dev.sh1on.amlethmp;

import dev.sh1on.amlethmp.common.template.controller.AmlethMPController;
import dev.sh1on.amlethmp.common.template.service.AmlethMPService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;

/**
 * Lớp khởi chạy hệ thống backend của ứng dụng <a href="https://github.com/henry0337/AmlethMP">AmlethMP</a>.
 * @author <a href="https://github.com/henry0337">S3lena</a>
 * @author <a href="https://github.com/AdorableDandelion25">Patricia</a>
 */
@SpringBootApplication
@ComponentScan(basePackages = "dev.sh1on.amlethmp", includeFilters = @ComponentScan.Filter(
		type = FilterType.ASSIGNABLE_TYPE,
		classes = {AmlethMPController.class, AmlethMPService.class}
))
@EnableR2dbcAuditing
public class AmlethMPBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(AmlethMPBackendApplication.class, args);
	}
}
