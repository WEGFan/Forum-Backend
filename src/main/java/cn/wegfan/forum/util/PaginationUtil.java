package cn.wegfan.forum.util;

import cn.wegfan.forum.constant.Constant;

/**
 * 分页工具类
 */
public class PaginationUtil {

    /**
     * 将每页个数固定在 1 到 {@value Constant#MAX_PAGE_SIZE} 之间
     *
     * @param pageSize 每页个数
     */
    public static long clampPageSize(long pageSize) {
        return Math.max(Math.min(pageSize, Constant.MAX_PAGE_SIZE), 1);
    }

}
