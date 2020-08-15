package cn.wegfan.forum.config.orika;

import cn.wegfan.forum.util.ConstructableFromValue;
import cn.wegfan.forum.util.EnumUtil;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class ConstructableFromValueEnumConverter extends BidirectionalConverter<ConstructableFromValue<Object>, Object> {

    @Override
    public Object convertTo(ConstructableFromValue<Object> source, Type<Object> destinationType, MappingContext mappingContext) {
        return source.getEnumValue();
    }

    @Override
    public ConstructableFromValue<Object> convertFrom(Object source, Type<ConstructableFromValue<Object>> destinationType, MappingContext mappingContext) {
        return EnumUtil.fromEnumValue(destinationType.getRawType(), source);
    }

    @Override
    public boolean canConvert(Type<?> sourceType, Type<?> destinationType) {
        Type<?> enumType, valueType;
        if (isConstructableFromValueEnum(sourceType)) {
            enumType = sourceType;
            valueType = destinationType;
        } else if (isConstructableFromValueEnum(destinationType)) {
            enumType = destinationType;
            valueType = sourceType;
        } else {
            return false;
        }

        boolean typeMatch = Arrays.stream(enumType.getInterfaces())
                .anyMatch(i -> valueType.getRawType().equals(((Type<?>)i.getActualTypeArguments()[0]).getRawType()));
        return typeMatch;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object convert(Object source, Type<?> destinationType, MappingContext mappingContext) {
        if (source.getClass().isEnum()) {
            return convertTo((ConstructableFromValue<Object>)source, (Type<Object>)destinationType, mappingContext);
        } else {
            return convertFrom(source, (Type<ConstructableFromValue<Object>>)destinationType, mappingContext);
        }

    }

    private boolean isConstructableFromValueEnum(Type<?> type) {
        return type.isEnum() &&
                Arrays.stream(type.getInterfaces())
                        .anyMatch(i -> ConstructableFromValue.class.equals(i.getRawType()));
    }

}
