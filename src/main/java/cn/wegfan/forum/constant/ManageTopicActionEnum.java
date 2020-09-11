package cn.wegfan.forum.constant;

import cn.wegfan.forum.util.ConstructableFromValue;
import cn.wegfan.forum.util.EnumUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum ManageTopicActionEnum implements ConstructableFromValue<String> {
    /**
     * 删除
     */
    DELETE("delete", "删除"),
    /**
     * 置顶
     */
    PIN("pin", "置顶"),
    /**
     * 取消置顶
     */
    UNPIN("unpin", "取消置顶"),
    /**
     * 精华
     */
    FEATURE("feature", "精华"),
    /**
     * 取消精华
     */
    UNFEATURE("unfeature", "取消精华");

    @EnumValue
    private final String value;

    private final String name;

    ManageTopicActionEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public String getEnumValue() {
        return value;
    }

    public static ManageTopicActionEnum fromValue(String value) {
        return EnumUtil.fromEnumValue(ManageTopicActionEnum.class, value);
    }

    public static ManageTopicActionEnum fromValue(String value, ManageTopicActionEnum defaultEnum) {
        return EnumUtil.fromEnumValue(ManageTopicActionEnum.class, value, defaultEnum);
    }

}
