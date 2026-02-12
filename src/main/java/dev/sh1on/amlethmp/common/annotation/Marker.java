package dev.sh1on.amlethmp.common.annotation;

import java.lang.annotation.*;

/**
 * <b>[Marker Annotation]</b> <br>
 *
 * Chú thích giúp đánh dấu một <b>class/interface</b> là một đối tượng dạng marker, ám chỉ <b>class/interface</b> được
 * đánh dấu sẽ luôn chỉ được sử dụng để cung cấp <b>kiểu runtime</b> cho trình biên dịch hoặc JVM để chúng có thêm các
 * thông tin bổ sung cho đối tượng đó.
 *
 * @author <a href="https://github.com/AdorableDandelion25">Stella</a>
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Marker { }
