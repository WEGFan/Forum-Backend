package cn.wegfan.forum.config.orika;

import cn.wegfan.forum.model.entity.User;
import cn.wegfan.forum.model.vo.response.IdNameResponseVo;
import cn.wegfan.forum.model.vo.response.UserSearchResponseVo;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;
import org.springframework.stereotype.Component;

@Component
public class CustomOrikaMapping implements OrikaMapperFactoryConfigurer {

    @Override
    public void configure(MapperFactory mapperFactory) {
        mapperFactory.classMap(User.class, UserSearchResponseVo.class)
                .byDefault()
                .customize(new CustomMapper<User, UserSearchResponseVo>() {
                    @Override
                    public void mapAtoB(User user, UserSearchResponseVo userSearchResponseVo, MappingContext context) {
                        super.mapAtoB(user, userSearchResponseVo, context);
                        // 由于deleteTime为null时不会走转换器，只能最后手动设置一下
                        userSearchResponseVo.setDeleted(user.getDeleteTime() != null);
                    }
                })
                .register();

        mapperFactory.classMap(User.class, IdNameResponseVo.class)
                .fieldMap("username", "name").add()
                .byDefault()
                .register();
    }

}
