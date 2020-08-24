package cn.wegfan.forum.config.orika;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.converter.ConverterFactory;
import ma.glasnost.orika.converter.builtin.BuiltinConverters;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.impl.generator.EclipseJdtCompilerStrategy;
import ma.glasnost.orika.impl.generator.JavassistCompilerStrategy;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryBuilderConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CustomOrikaMapperFactoryBuilderConfigurer implements OrikaMapperFactoryBuilderConfigurer {

    /**
     * 当前生效的项目配置名称
     */
    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Override
    public void configure(DefaultMapperFactory.MapperFactoryBuilder<?, ?> mapperFactoryBuilder) {
        MapperFactory mapperFactory = mapperFactoryBuilder
                // 先不使用内置转换器，让自定义转换器先注册
                .useBuiltinConverters(false)
                .dumpStateOnException(true)
                .compilerStrategy("dev".equals(activeProfile)
                        ? new EclipseJdtCompilerStrategy()
                        : new JavassistCompilerStrategy())
                .build();

        ConverterFactory converterFactory = mapperFactory.getConverterFactory();

        converterFactory.registerConverter(new ConstructableFromValueEnumConverter());

        BuiltinConverters.register(converterFactory);
    }

}
