package dev.sh1on.amlethmp.common.shared.repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.regex.Pattern;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;

import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * <b>[API Customized Repository]</b> <br>
 * Repository được tùy chỉnh để đọc nội dung từ các file SQL và thực thi câu truy vấn bên trong tương ứng.
 * <p>
 * Để sử dụng chỉ cần inject lớp này vào các {@link org.springframework.stereotype.Component Component} khác khi 
 * mà cần các truy vấn phức tạp hơn.
 * </p>
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Repository
public class AmlethMPCustomRepository {

    private static final String SQL_LOCATION_PATTERN = "classpath:sql/**/*.sql";
    private static final Pattern SAFE_IDENTIFIER_SEGMENT_PATTERN = Pattern.compile("^[a-zA-Z_]\\w*$");

    private final DatabaseClient databaseClient;
    private final Map<String, String> sqlCache = new ConcurrentHashMap<>();

    public AmlethMPCustomRepository(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
        loadAllSqlFiles();
    }

    private void loadAllSqlFiles() {
        try {
            var resolver = new PathMatchingResourcePatternResolver();
            for (Resource resource : resolver.getResources(SQL_LOCATION_PATTERN)) {
                Assert.notNull(resource.getFilename(), "Tên file SQL không được null");
                try (var inputStream = resource.getInputStream()) {
                    sqlCache.put(resource.getFilename(),
                            StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8));
                }
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Không đọc được file SQL trong classpath:sql/", exception);
        }
    }

    /**
     * Thực thi truy vấn trả về nhiều dòng.
     *
     * @param sqlFile     Tên file SQL trong {@code classpath:sql/}
     * @param identifiers Map tên định danh động ({@code ${key}}) sang giá trị thật, đã validate
     * @param params      Map tham số giá trị ({@code :key}) để bind
     * @param mapper      Hàm chuyển {@link Row} thành đối tượng kết quả
     * @return {@link Flux} kết quả từng dòng
     */
    public <T> Flux<T> queryMany(String sqlFile, Map<String, String> identifiers, Map<String, Object> params,
                                BiFunction<Row, RowMetadata, T> mapper) {
        return databaseClient.sql(resolveSql(sqlFile, identifiers))
                .bindValues(params)
                .map(mapper)
                .all();
    }

    public <T> Flux<T> queryMany(String sqlFile, Map<String, Object> params, BiFunction<Row, RowMetadata, T> mapper) {
        return queryMany(sqlFile, Map.of(), params, mapper);
    }

    /**
     * Thực thi truy vấn trả về đúng 1 dòng.
     *
     * @param sqlFile     Tên file SQL trong {@code classpath:sql/}
     * @param identifiers Map tên định danh động ({@code ${key}}) sang giá trị thật, đã validate
     * @param params      Map tham số giá trị ({@code :key}) để bind
     * @param mapper      Hàm chuyển {@link Row} thành đối tượng kết quả
     * @return {@link Mono} kết quả duy nhất
     */
    public <T> Mono<T> queryOne(String sqlFile, Map<String, String> identifiers, Map<String, Object> params,
                                BiFunction<Row, RowMetadata, T> mapper) {
        return databaseClient.sql(resolveSql(sqlFile, identifiers))
                .bindValues(params)
                .map(mapper)
                .one();
    }

    public <T> Mono<T> queryOne(String sqlFile, Map<String, Object> params, BiFunction<Row, RowMetadata, T> mapper) {
        return queryOne(sqlFile, Map.of(), params, mapper);
    }

    /**
     * Thực thi câu lệnh thay đổi dữ liệu (INSERT/UPDATE/DELETE).
     *
     * @param sqlFile     Tên file SQL trong {@code classpath:sql/}
     * @param identifiers Map tên định danh động ({@code ${key}}) sang giá trị thật, đã validate
     * @param params      Map tham số giá trị ({@code :key}) để bind
     * @return {@link Mono} số dòng bị ảnh hưởng
     */
    public Mono<Long> execute(String sqlFile, Map<String, String> identifiers, Map<String, Object> params) {
        return databaseClient.sql(resolveSql(sqlFile, identifiers))
                .bindValues(params)
                .fetch()
                .rowsUpdated();
    }

    public Mono<Long> execute(String sqlFile, Map<String, Object> params) {
        return execute(sqlFile, Map.of(), params);
    }

    private String resolveSql(String sqlFile, Map<String, String> identifiers) {
        String sql = sqlCache.get(sqlFile);
        Assert.state(sql != null, "Không tìm thấy file SQL: " + sqlFile);
        return substituteIdentifiers(sql, identifiers);
    }

    private String substituteIdentifiers(String sql, Map<String, String> identifiers) {
        String result = sql;
        for (Map.Entry<String, String> entry : identifiers.entrySet()) {
            String identifier = entry.getValue();
            for (String segment : identifier.split("\\.", -1)) {
                Assert.state(SAFE_IDENTIFIER_SEGMENT_PATTERN.matcher(segment).matches(),
                        "Định danh không hợp lệ, nghi ngờ SQL Injection: " + identifier);
            }
            result = result.replace("${" + entry.getKey() + "}", identifier);
        }
        return result;
    }
}
