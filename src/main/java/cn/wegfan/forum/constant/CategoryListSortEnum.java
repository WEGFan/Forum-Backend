package cn.wegfan.forum.constant;

import cn.wegfan.forum.util.ConstructableFromValue;
import cn.wegfan.forum.util.EnumUtil;
import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum CategoryListSortEnum implements ConstructableFromValue<String> {
    /**
     * 编号
     */
    ID("id", "`category`.`id` ASC"),
    /**
     * 顺序
     */
    ORDER("order", "`category`.`order` ASC, `category`.`id` ASC");

    @EnumValue
    private final String value;

    private final String orderBySql;

    CategoryListSortEnum(String value, String orderBySql) {
        this.value = value;
        this.orderBySql = orderBySql;
    }

    @Override
    public String getEnumValue() {
        return value;
    }

    public static CategoryListSortEnum fromValue(String value) {
        return EnumUtil.fromEnumValue(CategoryListSortEnum.class, value);
    }

    public static CategoryListSortEnum fromValue(String value, CategoryListSortEnum defaultEnum) {
        return EnumUtil.fromEnumValue(CategoryListSortEnum.class, value, defaultEnum);
    }

}
