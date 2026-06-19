package dev.sh1on.amlethmp.common.config;

import dev.sh1on.amlethmp.user.model.User;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * <p>Lớp cung cấp thông tin về người dùng hiện tại (Auditor) cho hệ thống.</p>
 * <p>Được sử dụng trong việc tự động lưu trữ người tạo hoặc người cập nhật thực thể.</p>
 *
 * @author <a href="https://github.com/AdorableDandelion25">Himekawa</a>
 */
@Component
class AuditorAware implements ReactiveAuditorAware<String> {
    @Override
    public Mono<String> getCurrentAuditor() {
        return ReactiveSecurityContextHolder.getContext()
                .mapNotNull(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(principal -> principal instanceof User user ? user.getId().toString() : "")
                .switchIfEmpty(Mono.just("system"));
    }
}
