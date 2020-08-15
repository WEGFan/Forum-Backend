package cn.wegfan.forum.constant;

import cn.wegfan.forum.util.ConstructableFromValue;
import cn.wegfan.forum.util.EnumUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum SexEnum implements ConstructableFromValue<Integer> {
    /**
     * 男
     */
    MALE(0, "男"),
    /**
     * 女
     */
    FEMALE(1, "女"),
    /**
     * 保密
     */
    SECRET(2, "保密");

    @EnumValue
    private final Integer value;

    private final String name;

    SexEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public Integer getEnumValue() {
        return value;
    }

    public static SexEnum fromValue(Integer value) {
        return EnumUtil.fromEnumValue(SexEnum.class, value);
    }

}
