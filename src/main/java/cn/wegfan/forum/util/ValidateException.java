package cn.wegfan.forum.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * 参数校验异常
 */
@Getter
@Setter
@SuppressWarnings("serial")
public class ValidateException extends RuntimeException {

    private List<ObjectError> errorList;

    private Object inputObject;

    public ValidateException(List<ObjectError> errorList, Object inputObject) {
        this.errorList = errorList;
        this.inputObject = inputObject;
    }

}
