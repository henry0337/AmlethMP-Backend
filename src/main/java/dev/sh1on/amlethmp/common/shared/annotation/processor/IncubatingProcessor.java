package dev.sh1on.amlethmp.common.shared.annotation.processor;

import dev.sh1on.amlethmp.common.shared.annotation.Incubating;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Processor xử lý chú thích {@link Incubating}.
 * <p>
 * Ghi log cảnh báo khi một phương thức hoặc lớp đang trong giai đoạn phát triển được sử dụng.
 * </p>
 */
@Aspect
@Component
@Slf4j
@SuppressWarnings("unused")
public class IncubatingProcessor {
    /**
     * Cache để lưu trữ các phương thức đã được log để tránh spam log.
     */
    private static final Set<String> loggedMethods = Collections.newSetFromMap(new ConcurrentHashMap<>());

    @Pointcut("@annotation(dev.sh1on.amlethmp.common.shared.annotation.Incubating) " +
            "|| @within(dev.sh1on.amlethmp.common.shared.annotation.Incubating)")
    public void incubatingPointcut() { }

    @Before("incubatingPointcut()")
    public void logIncubatingUsage(@NonNull JoinPoint joinPoint) {
        var methodName = joinPoint.getSignature().toShortString();
        
        // Chỉ log một lần cho mỗi phương thức trong suốt vòng đời ứng dụng để tránh làm đầy console
        if (loggedMethods.add(methodName)) {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Class<?> declaringType = signature.getDeclaringType();
            
            boolean isClassAnnotated = declaringType.isAnnotationPresent(Incubating.class);
            
            if (isClassAnnotated) {
                log.warn("[INCUBATING] Lớp '{}' đang trong giai đoạn phát triển. Hãy cẩn trọng khi sử dụng.", 
                    declaringType.getSimpleName());
            } else {
                log.warn("[INCUBATING] Phương thức '{}' trong '{}' đang trong giai đoạn phát triển. Hãy cẩn trọng khi sử dụng.", 
                    signature.getName(), declaringType.getSimpleName());
            }
        }
    }
}
