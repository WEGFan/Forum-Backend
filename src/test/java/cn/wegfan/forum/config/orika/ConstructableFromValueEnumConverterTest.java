package cn.wegfan.forum.config.orika;

import cn.wegfan.forum.util.ConstructableFromValue;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.converter.builtin.BuiltinConverters;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 测试 {@link ConstructableFromValueEnumConverter} 转换器
 */
@SpringBootTest
@Slf4j
class ConstructableFromValueEnumConverterTest {

    @ToString
    @Getter
    enum SexEnum implements ConstructableFromValue<Integer> {
        MALE(0), FEMALE(1), SECRET(2);

        private Integer value;

        SexEnum(Integer value) {
            this.value = value;
        }

        @Override
        public Integer getEnumValue() {
            return value;
        }
    }

    @ToString
    @Getter
    enum UserRoleEnum implements ConstructableFromValue<String> {
        NORMAL("normal"), BOARD_ADMIN("board_admin"), ADMIN("admin");

        private String value;

        UserRoleEnum(String value) {
            this.value = value;
        }

        @Override
        public String getEnumValue() {
            return value;
        }
    }

    @Data
    static class User {

        // enum -> integer
        private SexEnum sex;

        // enum -> string
        private UserRoleEnum role;

        // enum -> enum
        private SexEnum sex2;

    }

    @Data
    static class UserVo {

        private Integer sex;

        private String role;

        private SexEnum sex2;

    }

    @Test
    void testConstructableFromValueEnumConverter() {
        UserRoleEnum roleEnum = UserRoleEnum.BOARD_ADMIN;
        SexEnum sexEnum = SexEnum.SECRET;

        User user = new User();
        user.setRole(roleEnum);
        user.setSex(sexEnum);
        user.setSex2(sexEnum);
        log.info("user = {}", user);

        MapperFactory mapperFactory = new DefaultMapperFactory.Builder()
                .useBuiltinConverters(false)
                .build();
        ConverterFactory converterFactory = mapperFactory.getConverterFactory();
        converterFactory.registerConverter(new ConstructableFromValueEnumConverter());
        BuiltinConverters.register(converterFactory);
        mapperFactory.classMap(User.class, UserVo.class)
                .byDefault()
                .register();
        BoundMapperFacade<User, UserVo> mapperFacade = mapperFactory.getMapperFacade(User.class, UserVo.class);

        UserVo userVo = mapperFacade.map(user);
        log.info("userVo = {}", userVo);
        Assertions.assertEquals(sexEnum.getEnumValue(), userVo.sex);
        Assertions.assertEquals(roleEnum.getEnumValue(), userVo.role);
        Assertions.assertEquals(sexEnum, userVo.sex2);

        User mappedUser = mapperFacade.mapReverse(userVo);
        log.info("mappedUser = {}", mappedUser);
        Assertions.assertEquals(sexEnum, mappedUser.sex);
        Assertions.assertEquals(roleEnum, mappedUser.role);
        Assertions.assertEquals(sexEnum, mappedUser.sex2);
    }

}