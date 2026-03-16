package dev.sh1on.amlethmp.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Mono;

/**
 * <p>Lớp cung cấp thông tin về người dùng hiện tại (Auditor) cho hệ thống.</p>
 * <p>Được sử dụng trong việc tự động lưu trữ người tạo hoặc người cập nhật thực thể.</p>
 *
 * @author <a href="https://github.com/AdorableDandelion25">Patricia</a>
 */
@Configuration
public class AuditorAware implements ReactiveAuditorAware<String> {

    /**
     * <p>Lấy tên người dùng hiện tại từ <b>SecurityContext</b>.</p>
     *
     * @return <b>Mono</b> chứa tên người dùng hiện tại hoặc "Unknown User" nếu không xác thực.
     */
    @Override
    public Mono<String> getCurrentAuditor() {
        return ReactiveSecurityContextHolder.getContext().map(securityContext -> {
            Authentication authentication = securityContext.getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) return "Unknown User";

            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails auditor) {
                return auditor.getUsername();
            }
            return principal != null ? principal.toString() : "Unknown User";
        });
    }
}
