package dev.sh1on.amlethmp.common.template.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Repository đặc thù chứa các phương thức truy vấn có thể được sử dụng nhiều trong dự án.
 * @param <T> Kiểu domain mà repository này sẽ quản lý
 * @param <ID> Kiểu của ID thuộc domain mà repository này sẽ quản lý
 * @author <a href="https://github.com/henry0337">Amleth</a>
 */
@NoRepositoryBean
public interface AmlethMPRepository<T, ID> extends R2dbcRepository<T, ID> { }
