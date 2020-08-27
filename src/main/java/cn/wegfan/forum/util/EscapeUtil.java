package cn.wegfan.forum.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 转义工具类
 */
public class EscapeUtil {

    /**
     * 转义 SQL 的 LIKE 查询
     *
     * @param str 要转义的字符串
     *
     * @return 转义后的字符串
     */
    public static String escapeSqlLike(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }
        // 转义\%_
        return str.replaceAll("([\\\\%_])", "\\\\$1");
    }

}
