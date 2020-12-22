package i18n;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

@Configuration(proxyBeanMethods = false)
public class BasicI18nAutoConfiguration {

    @Primary
    @Bean
    public ReloadableResourceBundleMessageSource messageSource(ApplicationContext context, @Value("${spring.messages.basename:messages}") String messageName) {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(context.getClassLoader());
        List<String> baseNames = new LinkedList<>();
        String allBaseName = "base_messages" + "," +messageName;
        for (String name: allBaseName.split(",") ) {
            if (Stream.of(getResources(resolver, name)).anyMatch(Resource::exists)) {
                baseNames.add("classpath:/" + name);
            }
        }
        messageSource.setBasenames(baseNames.toArray(new String[0]));
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    private Resource[] getResources(PathMatchingResourcePatternResolver resolver, String name) {
        String target = "classpath*:" + name.replace('.', '/') + ".properties";
        try {
            return resolver.getResources(target);
        } catch (Exception ex) {
            try {
                throw ex;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
