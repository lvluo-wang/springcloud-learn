package log;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration(proxyBeanMethods = false)
@Import({MyRequestLoggingFilter.class})
public class BasicLogsAutoConfiguration {
}
