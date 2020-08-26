package cn.wegfan.forum.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.SmartValidator;

import java.util.List;

@Component
public class ValidateUtil {

    private static SmartValidator validator;

    @Autowired
    public void setValidator(SmartValidator validator) {
        ValidateUtil.validator = validator;
    }

    public static List<ObjectError> validate(Object object) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(object, "");
        validator.validate(object, bindingResult);
        return bindingResult.getAllErrors();
    }

    public static void tryValidateAndThrow(Object object) {
        List<ObjectError> errorList = validate(object);
        if (!errorList.isEmpty()) {
            throw new ValidateException(errorList, object);
        }
    }

}
