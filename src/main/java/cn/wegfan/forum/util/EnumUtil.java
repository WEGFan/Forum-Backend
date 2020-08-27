package cn.wegfan.forum.util;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * 枚举工具类
 */
public class EnumUtil {

    /**
     * 根据枚举值构造枚举对象，如果有多个相同的值则返回第一个枚举对象，如果不存在则抛出 {@link NoSuchElementException} 异常
     *
     * @param cls   枚举类
     * @param value 枚举值
     *
     * @return 枚举对象
     *
     * @throws NoSuchElementException 值不存在时抛出
     */
    public static <T extends ConstructableFromValue<V>, V> T fromEnumValue(Class<T> cls, V value)
            throws NoSuchElementException {
        return Arrays.stream(cls.getEnumConstants())
                .filter(e -> e.getEnumValue().equals(value))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    /**
     * 根据枚举值构造枚举对象，如果有多个相同的值则返回第一个枚举对象，如果不存在则返回 {@code defaultEnum}
     *
     * @param cls         枚举类
     * @param value       枚举值
     * @param defaultEnum 默认枚举对象
     *
     * @return 枚举对象
     */
    public static <T extends ConstructableFromValue<V>, V> T fromEnumValue(Class<T> cls, V value, T defaultEnum) {
        try {
            return fromEnumValue(cls, value);
        } catch (NoSuchElementException e) {
            return defaultEnum;
        }
    }

}
