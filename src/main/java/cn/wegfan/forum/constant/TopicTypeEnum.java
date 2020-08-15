package cn.wegfan.forum.constant;

import cn.wegfan.forum.util.ConstructableFromValue;
import cn.wegfan.forum.util.EnumUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum TopicTypeEnum implements ConstructableFromValue<Integer> {
    /**
     * 普通主题
     */
    NORMAL(0, "普通主题"),
    /**
     * 公告
     */
    ANNOUNCEMENT(1, "公告");

    @EnumValue
    private final Integer value;

    private final String name;

    TopicTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public Integer getEnumValue() {
        return value;
    }

    public static TopicTypeEnum fromValue(Integer value) {
        return EnumUtil.fromEnumValue(TopicTypeEnum.class, value);
    }

}
