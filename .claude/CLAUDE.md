# CLAUDE.md

File này cung cấp bối cảnh cho Claude Code khi làm việc trong repo này.

## Tổng quan

Backend cho AmlethMP — ứng dụng Spring Boot **reactive hoàn toàn**, dùng
Spring WebFlux + R2DBC (PostgreSQL) + Reactive Redis. Java 21, Gradle (Kotlin DSL) kèm version
catalog. **Không có tầng data blocking (JPA/JDBC)** — mọi thứ đều trả về `Mono`/`Flux`.

Dự án dùng các dependency bleeding-edge/milestone/snapshot (Spring Boot 4.0.7,
`spring-boot-aop` 4.0.0-M2, `resilience4j` 2.4.0). Repository khai báo trong
`settings.gradle.kts` gồm `mavenLocal()`, `mavenCentral()`, `jitpack.io`. Code trong dự án viết bằng
tiếng Việt — Javadoc, comment và message `i18n` đều bằng tiếng Việt.

## Thư viện scaffolding nội bộ (`dev.myrlennia237`) — đọc trước tiên

Phần lớn lớp base/template mà app này kế thừa **không nằm trong repo này**. Chúng đến từ thư viện
scaffolding reactive nội bộ **`dev.myrlennia237:webflux:0.1.0-SNAPSHOT`** (và `:shared`), được phát
triển ở **repo anh em `../Kotlin-Spring-Utils`** và cài vào **`mavenLocal()`**.

- Cache SNAPSHOT bị tắt trong `build.gradle.kts` (`resolutionStrategy.cacheChangingModulesFor(0, …)`),
  nên publish mới sẽ được nhận ngay ở lần build tiếp theo, không cần bump version.
- Muốn đổi hành vi thư viện: sửa source ở `../Kotlin-Spring-Utils`, rồi chạy
  `./gradlew :webflux:publishToMavenLocal` **ở repo đó** trước khi build lại app này.
- Thư viện có auto-configuration (`SpringReactiveAutoConfiguration`) đăng ký sẵn các bean:
  `responseHelper`, `reactorHelper`, `auditorAware` (reactive `AsyncAuditorAware`), `i18nService`,
  `reactiveRedisService`, `reactiveHttpClient`, `mailService`, `r2dbcCustomConversions`. App này
  **không** tự định nghĩa lại các bean đó.

> Nếu task cần sửa **tài liệu** (KDoc/comment/docs) bên trong `Kotlin-Spring-Utils`, hỏi user trước
> khi động vào.

## Lệnh hay dùng

Dùng Gradle wrapper (`./gradlew` trên Unix, `gradlew.bat` trên Windows/PowerShell).

```bash
./gradlew build              # compile + test + checkstyle
./gradlew bootRun            # chạy app (mặc định profile 'dev')
./gradlew test               # chạy toàn bộ test
./gradlew test --tests "dev.sh1on.amlethmp.AmlethMPBackendApplicationTests"
./gradlew test --tests "*.UserServiceTest.findAll*"
./gradlew checkstyleMain     # lint main sources (config/checkstyle/checkstyle.xml)
./gradlew bootBuildImage     # build OCI image (paketo buildpacks)
```

- Thêm `-Pdev` để tắt timestamp/version comment do MapStruct sinh ra và bật log verbose cho mapper.
- Checkstyle cấu hình `isIgnoreFailures = true` — vi phạm style chỉ **cảnh báo, không fail build**.
- Nếu build lỗi không resolve được `dev.myrlennia237:*`, publish thư viện từ
  `../Kotlin-Spring-Utils` vào mavenLocal trước (xem mục trên).

## Môi trường local

- `compose.yaml` cấp Postgres dùng-một-lần (`postgres:18-alpine`). `spring-boot-docker-compose` nằm
  trong configuration `developmentOnly`, nên **Compose tự khởi động** cùng profile dev.
- Config theo profile: `application.yaml` kích hoạt profile `dev` mặc định, kéo theo
  `application-dev.yaml` / `application-prod.yaml`.
- Secret lấy từ file `.env` (load qua `spring-dotenv`; `.env` đã gitignore). Biến bắt buộc cho profile
  dev: `JWT_SECRET`, `DB_HOST`, `DB_USERNAME`, `DB_PASSWORD`, `LIQUIBASE_HOST`,
  `REDIS_HOST`/`REDIS_PORT`/`REDIS_PASSWORD`, `MAIL_ACCOUNT`/`MAIL_PASSWORD`.
- Schema quản lý bởi **Liquibase** (`src/main/resources/db/changelog/db.changelog-master.xml`), chạy
  lúc startup. R2DBC không tự tạo bảng — mỗi thay đổi schema phải thêm changeSet mới.

## Kiến trúc

### Cấu trúc package (`dev.sh1on.amlethmp`)
- **Feature modules** (`auth`, `user`, `song`) đều theo layout vertical-slice giống nhau:
  `controller` / `service` / `repository` / `model` / `dto` / `mapper`.
- **`common.AmlethMPEndpoint`** — một lớp final duy nhất chứa **toàn bộ** route constant, nhóm theo
  module thành nested class (`Auth`, `User`, `Song`, `Docs`), mỗi class có `BASE`, `BY_ID`, `DISABLE`,
  `ENABLE`, v.v.
- **`common.config`** — config Spring chia theo profile (`development/`, `production/`), cộng thêm
  `LocalizationConfig` không phụ thuộc profile (`MessageSource` + `LocaleContextResolver`).
- **`common.event`** — listener lúc startup (`SonarLintInitializer`, `SwaggerUiInitializer`).
- **`common.shared`** — helper cross-cutting: `constant` (`AppConstant`, gồm
  `AppConstant.MessageCode` cho i18n) và `exception` (`GlobalExceptionHandler`,
  `RecordNotFoundException`, `TypeNotMatchException`).

### CRUD template pattern (mấu chốt cần hiểu)
CRUD của feature được xây bằng cách kế thừa lớp base **từ thư viện**, tham số hoá bởi
`<T, I1, I2>` = **output DTO, create DTO, update DTO** (key luôn là `UUID`).

- **Controller** kế thừa `AbstractCrudController<T, I1, I2>`, dùng annotation của thư viện
  **`@ApiController` / `@ApiMethod` / `@ApiParameter`** — **không** dùng
  `@RestController`/`@RequestMapping` của Spring. (`AuthController` là ngoại lệ vì không phải CRUD
  thuần.)
- **Service** kế thừa `AbstractCrudService<T, I1, I2>` (kế thừa `BaseReactiveService`), có sẵn
  `reactorHelper`, `auditorAware`, `i18nService`.
- **Repository** kế thừa `ModifiedR2dbcRepository<T>` (gộp `ReactiveCrudRepository` +
  `ReactiveSortingRepository`, thêm `findAllBy(Pageable)`; không có QueryDSL — `spring-data-r2dbc` chưa
  implement `ReactiveQuerydslPredicateExecutor`).
- **Entity** kế thừa `dev.myrlennia237.template.entity.Entity` (Kotlin class) — có sẵn `id`, auditing,
  optimistic-lock `@Version`, và **soft-delete built-in** (`disabled`,
  `lastDisabledAt`/`lastDisabledBy`, `markAsDeleted(auditor)`/`restore()`/`isDisabled()`).

`SongController`/`SongService`/`SongRepository`/`Song` (hoặc module `user`) là implementation tham
chiếu — copy một cái khi thêm entity mới. Chi tiết convention khi thêm/sửa CRUD (pagination, mapping,
route constant, soft-delete column...) xem Rule/Skill riêng cho CRUD.

### Bảo mật
- Auth stateless bằng **JWT bearer-token** qua Spring Security Reactive.
  `auth.config.SecurityConfiguration` (annotate `@EnableReactiveSecurityCustomization`) build
  `SecurityWebFilterChain` với `AuthenticationWebFilter` validate header qua `JwtService` +
  `CustomUserDetailsService`.
- Module `auth`: `AuthController`/`AuthService` (`JwtAuthenticationService`) xử lý login/register;
  `TokenBlacklistService` phục vụ logout/revoke. Password dùng `BCryptPasswordEncoder`.
- Path permit-all khai báo trong `SecurityConfiguration` (`/api/auth/v1/**`, `/api/user/v1/**`, path
  springdoc/swagger). Cập nhật allowlist này khi thêm endpoint cần truy cập không cần auth.

### Hạ tầng khác
- **i18n**: text hiển thị cho user đi qua `i18nService.translate(code, …)` của thư viện, key khai báo
  ở `AppConstant.MessageCode`, resolve từ `src/main/resources/i18n/messages*.properties`
  (default/en/ja/vi). Ưu tiên cách này hơn string literal.
- **Redis**: dùng `ReactiveRedisService` của thư viện để ghi (`set(key, value[, ttl])`) — thư viện
  **không có `get`**, đọc thì inject thẳng `ReactiveStringRedisTemplate` của Spring.
- **Resilience4j** circuit-breaker/retry đã bật; HTTP outbound dùng `ReactiveHttpClient` của thư viện
  (wrap `WebClient` reactive).
- Đã wire nhưng chưa trung tâm: Kafka (Streams + starter), Azure Storage starter, Sentry, Spring Mail
  (`MailService`). Apache POI có mặt nhưng đang comment out.
- API docs: springdoc OpenAPI (WebFlux UI) tại `/swagger-ui.html` / `/v3/api-docs`.

## Nguyên tắc reactive cần nhớ
- Không bao giờ gọi code blocking trên reactive thread — giữ `Mono`/`Flux` xuyên suốt.
- Ưu tiên helper của thư viện: `CommonUtils.requireNonNull` (`dev.myrlennia237.util`),
  `ReactorHelper.discardReturnValue` (biến `Mono<T>` save thành `Mono<Void>`).
- Pagination làm thủ công trong service (`findAllBy(pageable)` + `count()` zip vào `PageImpl`, rồi
  `PagedResponse.from`), vì repository R2DBC không tự trả `Page`.

## Phong cách giao tiếp (Communication Style)
Nói kiểu người tiền sử (tiếng Việt). Ngắn gọn, cộc lốc tối đa.
- Cắt bỏ hoàn toàn: Lời chào hỏi xã giao, từ xưng hô (mình/bạn/dạ/ạ), từ đệm và từ tình thái (thì/là/mà/nhé/nè/nhỉ), câu dẫn rườm rà.
- KHÔNG lặp lại câu hỏi hay yêu cầu của người dùng.
- Giữ BẢO TOÀN 100% nguyên văn: Mọi đoạn code, câu lệnh terminal, và log lỗi (không dịch, không sửa đổi ký tự).
