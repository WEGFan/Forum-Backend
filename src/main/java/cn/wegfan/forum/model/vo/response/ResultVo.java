package cn.wegfan.forum.model.vo.response;

import cn.wegfan.forum.constant.BusinessErrorEnum;
import cn.wegfan.forum.util.BusinessException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 接口返回对象
 */
@Slf4j
@Getter
@Setter
@ToString
public class ResultVo {

    /**
     * 数据
     */
    private Object data;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 参数校验错误信息
     */
    private Map<String, List<String>> errorData;

    public ResultVo(Object data, Integer code, String msg, Map<String, List<String>> errorData) {
        this.data = data;
        this.code = code;
        this.msg = msg;
        this.errorData = errorData;
        log.info("{}", this);
    }

    public static ResultVo success(Object data) {
        return new ResultVo(data, 200, "success", null);
    }

    public static ResultVo error(Integer code, String msg) {
        return new ResultVo(null, code, msg, null);
    }

    public static ResultVo businessError(BusinessException exception) {
        return new ResultVo(null, exception.getCode(), exception.getMessage(), null);
    }

    public static ResultVo validationError(List<ObjectError> errorList) {
        Map<String, List<String>> errorData = new HashMap<>();
        for (ObjectError error : errorList) {
            FieldError fieldError = (FieldError)error;
            String fieldName = fieldError.getField();
            List<String> errorMessageList;
            if (errorData.containsKey(fieldName)) {
                errorMessageList = errorData.get(fieldName);
            } else {
                errorMessageList = new ArrayList<>();
                errorData.put(fieldName, errorMessageList);
            }
            errorMessageList.add(error.getDefaultMessage());
        }
        BusinessErrorEnum validationError = BusinessErrorEnum.ValidationError;
        return new ResultVo(null, validationError.getErrorCode(),
                validationError.getErrorMessage(), errorData);
    }

}
