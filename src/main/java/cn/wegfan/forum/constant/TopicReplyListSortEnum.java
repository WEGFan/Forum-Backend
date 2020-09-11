package cn.wegfan.forum.constant;

import cn.wegfan.forum.util.ConstructableFromValue;
import cn.wegfan.forum.util.EnumUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum TopicReplyListSortEnum implements ConstructableFromValue<String> {
    /**
     * 按回帖时间升序
     */
    REPLY_TIME_ASC("normal", "`reply_time` ASC"),
    /**
     * 按回帖时间降序
     */
    REPLY_TIME_DESC("reversed", "`reply_time` DESC");

    @EnumValue
    private final String value;

    private final String orderBySql;

    TopicReplyListSortEnum(String value, String orderBySql) {
        this.value = value;
        this.orderBySql = orderBySql;
    }

    @Override
    public String getEnumValue() {
        return value;
    }

    public static TopicReplyListSortEnum fromValue(String value) {
        return EnumUtil.fromEnumValue(TopicReplyListSortEnum.class, value);
    }

    public static TopicReplyListSortEnum fromValue(String value, TopicReplyListSortEnum defaultEnum) {
        return EnumUtil.fromEnumValue(TopicReplyListSortEnum.class, value, defaultEnum);
    }

}
