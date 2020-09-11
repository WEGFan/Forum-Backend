package cn.wegfan.forum.constant;

import lombok.Getter;
import lombok.ToString;

/**
 * 业务异常枚举
 */
@Getter
@ToString
public enum BusinessErrorEnum {
    NOT_IMPLEMENTED(-1, "功能暂未实现"),
    VALIDATION_ERROR(400, "参数校验错误"),
    UNAUTHORIZED(401, "没有权限"),
    USER_NOT_LOGIN(403, "用户未登录"),
    API_NOT_FOUND(404, "接口地址未找到"),
    INTERNAL_SERVER_ERROR(500, "内部服务器错误，请联系管理员"),
    LINK_NOT_FOUND(10000, "友情链接不存在"),
    WRONG_USERNAME_OR_PASSWORD(10001, "用户名或密码错误"),
    DUPLICATE_USERNAME(10002, "用户名重复"),
    DUPLICATE_CATEGORY_NAME(10003, "分区名称重复"),
    CATEGORY_NOT_FOUND(10004, "分区不存在"),
    BOARD_NOT_FOUND(10005, "板块不存在"),
    DUPLICATE_BOARD_NAME(10006, "板块名称重复"),
    WRONG_OLD_PASSWORD(10007, "旧密码错误"),
    CANT_DELETE_OWN_ACCOUNT(10008, "不能删除自己的帐号"),
    USER_NOT_FOUND(10009, "用户不存在"),
    DUPLICATE_EMAIL(10010, "邮箱已被使用"),
    CANT_SET_OWN_ACCOUNT_ADMIN(10011, "不能取消自己帐号的管理员"),
    CANT_SET_OWN_ACCOUNT_BAN_LOGIN(10012, "不能设置自己帐号禁止登录"),
    ACCOUNT_DISABLED(10013, "帐号被禁用"),
    UPLOAD_FILE_TYPE_NOT_ALLOWED(10019, "文件类型不正确"),
    WRONG_AVATAR_SIZE(10020, "头像尺寸不正确"),
    UPLOAD_FILE_TOO_LARGE(10018, "上传的文件过大"),
    TOPIC_NOT_FOUND(10021, "主题帖不存在"),
    FILE_NOT_FOUND(10022, "文件不存在"),
    ATTACHMENT_NOT_FOUND(10023, "附件不存在"),
    REPLY_NOT_FOUND(10024, "回复不存在"),
    SEND_MAIL_FAILED(10025, "邮件发送失败");

    /**
     * 错误代码
     */
    private final Integer errorCode;

    /**
     * 错误信息
     */
    private final String errorMessage;

    BusinessErrorEnum(Integer errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
