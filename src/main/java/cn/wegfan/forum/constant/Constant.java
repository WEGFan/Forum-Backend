package cn.wegfan.forum.constant;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class Constant {

    /**
     * 默认头像地址
     */
    public static final String DEFAULT_AVATAR_PATH = "/api/file/avatar/default.jpg";

    /**
     * shiro管理员权限字符串
     */
    public static final String SHIRO_PERMISSION_ADMIN = "admin";

    /**
     * 最大分页大小
     */
    public static final long MAX_PAGE_SIZE = 20;

    /**
     * 不分页的分页对象
     */
    public static final Page<?> UNPAGED_PAGE = new Page<>(1, -1);

}
