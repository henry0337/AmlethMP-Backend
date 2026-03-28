package dev.sh1on.amlethmp.common.shared.annotation;

import java.lang.annotation.*;

/**
 * <b>[Internal, Developer's Annotation only]</b> <br>
 * Chú thích đánh dấu một lớp/phương thức đang trong giai đoạn phát triển.
 * <p>
 * Chức năng được đánh dấu bằng chú thích này sẽ luôn ở trạng thái bị thay đổi bất cứ lúc nào mà không được thông báo trước,
 * hãy cẩn trọng khi dùng các chức năng đó.
 * </p>
 * @author <a href="https://github.com/henry0337">S3lena</a>
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Incubating { }
