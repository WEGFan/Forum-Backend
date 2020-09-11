package cn.wegfan.forum.constant;

import cn.wegfan.forum.util.ConstructableFromValue;
import cn.wegfan.forum.util.EnumUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum TopicListTypeEnum implements ConstructableFromValue<String> {
    /**
     * 普通主题+精华主题（无置顶无公告）
     */
    NORMAL_AND_FEATURED("normal"),
    /**
     * 精华主题
     */
    FEATURED("featured"),
    /**
     * 置顶主题
     */
    PINNED("pinned"),
    /**
     * 公告主题
     */
    ANNOUNCEMENT("announcement"),
    /**
     * 全部主题
     */
    ALL("all");

    @EnumValue
    private final String value;

    TopicListTypeEnum(String value) {
        this.value = value;
    }

    @Override
    public String getEnumValue() {
        return value;
    }

    public static TopicListTypeEnum fromValue(String value, TopicListTypeEnum defaultEnum) {
        return EnumUtil.fromEnumValue(TopicListTypeEnum.class, value, defaultEnum);
    }

}
