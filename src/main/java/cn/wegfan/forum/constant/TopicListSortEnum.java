package cn.wegfan.forum.constant;

import cn.wegfan.forum.util.ConstructableFromValue;
import cn.wegfan.forum.util.EnumUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum TopicListSortEnum implements ConstructableFromValue<String> {
    /**
     * 按最后回复时间降序
     */
    REPLY_TIME("replyTime", "`last_reply_time` DESC"),
    /**
     * 按发帖时间降序
     */
    SUBMIT_TIME("submitTime", "`submit_time` DESC"),
    /**
     * 按浏览次数降序
     */
    VIEW_COUNT("viewCount", "`view_count` DESC"),
    /**
     * 按回复数降序
     */
    REPLY_COUNT("replyCount", "`reply_count` DESC");

    @EnumValue
    private final String value;

    private final String orderBySql;

    TopicListSortEnum(String value, String orderBySql) {
        this.value = value;
        this.orderBySql = orderBySql;
    }

    @Override
    public String getEnumValue() {
        return value;
    }

    public static TopicListSortEnum fromValue(String value) {
        return EnumUtil.fromEnumValue(TopicListSortEnum.class, value);
    }

    public static TopicListSortEnum fromValue(String value, TopicListSortEnum defaultEnum) {
        return EnumUtil.fromEnumValue(TopicListSortEnum.class, value, defaultEnum);
    }

}
