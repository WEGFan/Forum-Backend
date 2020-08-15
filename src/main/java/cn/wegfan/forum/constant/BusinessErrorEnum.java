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
