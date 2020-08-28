package cn.wegfan.forum.constant;

import cn.wegfan.forum.util.ConstructableFromValue;
import cn.wegfan.forum.util.EnumUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum BoardListSortEnum implements ConstructableFromValue<String> {
    /**
     * 编号
     */
    ID("id", "`board`.`id` ASC"),
    /**
     * 顺序
     */
    ORDER("order", "`category`.`order` ASC, `category`.`id` ASC, " +
            "`board`.`order` ASC, `board`.`id` ASC");

    @EnumValue
    private final String value;

    private final String orderBySql;

    BoardListSortEnum(String value, String orderBySql) {
        this.value = value;
        this.orderBySql = orderBySql;
    }

    @Override
    public String getEnumValue() {
        return value;
    }

    public static BoardListSortEnum fromValue(String value) {
        return EnumUtil.fromEnumValue(BoardListSortEnum.class, value);
    }

    public static BoardListSortEnum fromValue(String value, BoardListSortEnum defaultEnum) {
        return EnumUtil.fromEnumValue(BoardListSortEnum.class, value, defaultEnum);
    }

}
