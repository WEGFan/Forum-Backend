package cn.wegfan.forum.util;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

/**
 * 测试 {@link EnumUtil} 工具类
 */
@Slf4j
class EnumUtilTest {

    @ToString
    @Getter
    enum TestEnum implements ConstructableFromValue<String> {
        A("a"), B("b"), C("a");

        private String value;

        TestEnum(String value) {
            this.value = value;
        }

        @Override
        public String getEnumValue() {
            return value;
        }
    }

    @Test
    void testFromEnumValue() {
        Assertions.assertEquals(TestEnum.A, EnumUtil.fromEnumValue(TestEnum.class, "a"));
        Assertions.assertEquals(TestEnum.B, EnumUtil.fromEnumValue(TestEnum.class, "b"));
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            EnumUtil.fromEnumValue(TestEnum.class, null);
        });
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            EnumUtil.fromEnumValue(TestEnum.class, "123");
        });
    }

    @Test
    void testFromEnumValueWithDefault() {
        Assertions.assertEquals(TestEnum.A, EnumUtil.fromEnumValue(TestEnum.class, "a", TestEnum.C));
        Assertions.assertEquals(TestEnum.B, EnumUtil.fromEnumValue(TestEnum.class, "b", TestEnum.C));
        Assertions.assertEquals(TestEnum.C, EnumUtil.fromEnumValue(TestEnum.class, "123", TestEnum.C));
        Assertions.assertEquals(TestEnum.C, EnumUtil.fromEnumValue(TestEnum.class, null, TestEnum.C));
    }

}