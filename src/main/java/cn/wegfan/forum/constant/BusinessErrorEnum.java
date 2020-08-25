package cn.wegfan.forum.constant;

import lombok.Getter;
import lombok.ToString;

/**
 * 业务异常枚举
 */
@Getter
@ToString
public enum BusinessErrorEnum {
    NotImplemented(-1, "功能暂未实现"),
    ValidationError(400, "参数校验错误"),
    Unauthorized(401, "没有权限"),
    UserNotLogin(403, "用户未登录"),
    NotFound(404, "接口地址未找到"),
    InternalServerError(500, "内部服务器错误，请联系管理员"),
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
    UploadFileTooLarge(10018, "上传的文件过大");

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
