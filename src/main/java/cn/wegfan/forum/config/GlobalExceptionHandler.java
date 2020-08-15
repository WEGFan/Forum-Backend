package cn.wegfan.forum.config;

import cn.wegfan.forum.constant.BusinessErrorEnum;
import cn.wegfan.forum.model.vo.response.ResultVo;
import cn.wegfan.forum.util.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.List;

/**
 * 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 当前生效的项目配置名称
     */
    @Value("${spring.profiles.active}")
    private String activeProfile;

    /**
     * 处理上传文件大小超过限制
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResultVo handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.warn("{}", e.getMessage());
        return ResultVo.businessError(new BusinessException(BusinessErrorEnum.UploadFileTooLarge));
    }

    /**
     * 处理没有权限的请求
     */
    @ExceptionHandler({UnauthorizedException.class, AuthorizationException.class})
    public ResultVo handleUnauthorizedException(Exception e) {
        log.warn("{}", e.getMessage());
        return ResultVo.businessError(new BusinessException(BusinessErrorEnum.Unauthorized));
    }

    /**
     * 处理请求参数校验失败
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVo handleInvalidArgumentException(MethodArgumentNotValidException e) {
        log.warn("参数校验失败，输入值：{}", e.getBindingResult().getTarget());
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        return ResultVo.validationError(errors);
    }

    /**
     * 处理业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public ResultVo handleBusinessException(BusinessException e) {
        log.warn("{}", e.getMessage());
        return ResultVo.businessError(e);
    }

    /**
     * 处理 GET 参数校验失败
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResultVo handleNumberFormatException(MethodArgumentTypeMismatchException e) {
        log.warn("{}", e.getMessage());
        return ResultVo.error(400, "搜索条件不合法");
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(Exception.class)
    public ResultVo handleException(Exception e) {
        log.error("", e);
        // 如果是生产环境则不显示错误信息
        if ("prod".equals(activeProfile)) {
            return ResultVo.businessError(new BusinessException(BusinessErrorEnum.InternalServerError));
        }
        return ResultVo.error(BusinessErrorEnum.InternalServerError.getErrorCode(),
                String.format("内部服务器错误，错误信息：%s", e.toString()));
    }

}