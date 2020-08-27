package cn.wegfan.forum.constant;

import cn.wegfan.forum.util.ConstructableFromValue;
import cn.wegfan.forum.util.EnumUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum UserTypeEnum implements ConstructableFromValue<String> {
    /**
     * 所有用户
     */
    ALL("all", "所有用户"),
    /**
     * 普通用户
     */
    NORMAL_USER("user", "普通用户"),
    /**
     * 版主
     */
    BOARD_ADMIN("boardAdmin", "版主"),
    /**
     * 分区版主
     */
    CATEGORY_ADMIN("categoryAdmin", "分区版主"),
    /**
     * 超级版主
     */
    SUPER_BOARD_ADMIN("superBoardAdmin", "超级版主"),
    /**
     * 管理员
     */
    ADMIN("admin", "管理员"),
    /**
     * 禁止访问/登录的用户
     */
    BAN_VISIT("banVisit", "禁止访问/登录"),
    /**
     * 禁止发帖/回帖的用户
     */
    BAN_REPLY("banReply", "禁止发帖/回帖");

    @EnumValue
    private final String value;

    private final String name;

    UserTypeEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public String getEnumValue() {
        return value;
    }

    public static UserTypeEnum fromValue(String value) {
        return EnumUtil.fromEnumValue(UserTypeEnum.class, value);
    }

    public static UserTypeEnum fromValue(String value, UserTypeEnum defaultEnum) {
        return EnumUtil.fromEnumValue(UserTypeEnum.class, value, defaultEnum);
    }
}
