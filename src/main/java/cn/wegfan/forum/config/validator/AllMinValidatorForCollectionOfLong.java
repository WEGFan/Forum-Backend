package cn.wegfan.forum.config.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;

public class AllMinValidatorForCollectionOfLong implements ConstraintValidator<AllMin, Collection<Long>> {

    private long minValue;

    @Override
    public void initialize(AllMin constraintAnnotation) {
        minValue = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Collection<Long> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        for (Long i : value) {
            if (i == null) {
                return false;
            }
            if (i.compareTo(minValue) < 0) {
                return false;
            }
        }
        return true;
    }

}