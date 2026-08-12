package dev.sh1on.amlethmp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <b>[Main Entrypoint]</b> <br>
 * Lớp khởi chạy hệ thống backend của ứng dụng <a href="https://github.com/henry0337/AmlethMP">AmlethMP</a>.
 *
 * @author <a href="https://github.com/henry0337">Myrlennia</a>
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@SpringBootApplication
public class AmlethMPBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(AmlethMPBackendApplication.class, args);
    }
}
