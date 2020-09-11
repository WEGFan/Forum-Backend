package cn.wegfan.forum.constant;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.MediaType;
import org.springframework.util.unit.DataSize;

import java.nio.file.Path;
import java.nio.file.Paths;

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

    /**
     * 附件存放目录
     */
    public static final Path ATTACHMENT_PATH = Paths.get("data", "attachment").toAbsolutePath();

    /**
     * 头像存放目录
     */
    public static final Path AVATAR_PATH = Paths.get("data", "avatar").toAbsolutePath();

    /**
     * 图片存放目录
     */
    public static final Path IMAGE_PATH = Paths.get("data", "image").toAbsolutePath();

    /**
     * 临时文件名前缀
     */
    public static final String TEMP_FILE_PREFIX = "forum_tmp_";

    /**
     * 头像允许的文件类型
     */
    public static final String[] ALLOWED_AVATAR_MEDIA_TYPES = new String[] {
            MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE
    };

    /**
     * 附件允许的文件类型
     */
    public static final String[] ALLOWED_IMAGE_MEDIA_TYPES = new String[] {
            MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE
    };

    /**
     * 头像最小尺寸
     */
    public static final Pair<Integer, Integer> AVATAR_MIN_SIZE = new ImmutablePair<>(100, 100);

    /**
     * 头像最大尺寸
     */
    public static final Pair<Integer, Integer> AVATAR_MAX_SIZE = new ImmutablePair<>(500, 500);

    /**
     * 获取头像文件接口地址
     */
    public static final String AVATAR_API_ENDPOINT = "/api/file/avatar/";

    /**
     * 获取附件文件接口地址
     */
    public static final String ATTACHMENT_API_ENDPOINT = "/api/file/attachment/";

    /**
     * 获取图片文件接口地址
     */
    public static final String IMAGE_API_ENDPOINT = "/api/file/image/";

    /**
     * 附件最大大小
     */
    public static final DataSize MAX_ATTACHMENT_SIZE = DataSize.ofMegabytes(2);

    /**
     * 主题帖列表最大图片数量
     */
    public static final int TOPIC_LIST_MAX_IMAGE_COUNT = 3;

    /**
     * 图片最大大小
     */
    public static final DataSize MAX_IMAGE_SIZE = DataSize.ofMegabytes(5);

}
