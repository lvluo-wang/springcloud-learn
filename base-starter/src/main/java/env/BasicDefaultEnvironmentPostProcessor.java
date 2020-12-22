package env;

import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.util.Set;

public class BasicDefaultEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final String PROFILE_LOCAL = "local";

    @Override
    @SneakyThrows
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Set<String> activeProfiles1 = Set.of(environment.getActiveProfiles());
        boolean loadLocal = activeProfiles1.contains(PROFILE_LOCAL);

        YamlPropertySourceLoader yamlLoader = new YamlPropertySourceLoader();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        int i = 0;
        for (Resource res : resolver.getResources("classpath*:/default*.yml")) {
            if (loadLocal && "default-local.yml".equals(res.getFilename())) {
                yamlLoader.load("default-local"+ i, res)
                        .forEach(environment.getPropertySources()::addLast);
            } else if("default.yml".equals(res.getFilename())) {
                yamlLoader.load("default" + i, res)
                        .forEach(environment.getPropertySources()::addLast);
            }
        }
    }
}
