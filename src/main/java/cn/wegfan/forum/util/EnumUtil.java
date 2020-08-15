package cn.wegfan.forum.util;

import java.util.Arrays;

public class EnumUtil {

    public static <T extends ConstructableFromValue<V>, V> T fromEnumValue(Class<T> cls, V value) {
        // TODO: 判断个数
        return Arrays.stream(cls.getEnumConstants()).filter(e -> e.getEnumValue().equals(value)).findFirst().get();
    }

}
