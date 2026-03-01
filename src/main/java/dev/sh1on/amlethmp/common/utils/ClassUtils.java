package dev.sh1on.amlethmp.common.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ClassUtils {

    /**
     * Trích xuất tên tất cả các thuộc tính (không chứa modifier {@code static}) từ lớp được chỉ định.
     *
     * @param clazz Lớp cần lấy tên thuộc tính
     * @return Danh sách chứa tất cả tên các thuộc tính thỏa mãn.
     * @author <a href="https://github.com/AdorableDandelion25">Patricia</a>
     */
    public static List<String> extractAllNonStaticPropertiesNameFrom(Class<?> clazz) {
        var names = new ArrayList<String>();
        while (clazz != null && clazz != Object.class) {
            Arrays.stream(clazz.getDeclaredFields())
                    .filter(f -> !Modifier.isStatic(f.getModifiers()))
                    .map(Field::getName)
                    .forEach(names::add);
            clazz = clazz.getSuperclass();
        }
        return names;
    }
}
