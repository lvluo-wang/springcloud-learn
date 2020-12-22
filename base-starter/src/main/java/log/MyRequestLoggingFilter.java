package log;

import org.slf4j.MDC;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

public class MyRequestLoggingFilter extends AbstractRequestLoggingFilter {

    @Override
    protected boolean shouldLog(javax.servlet.http.HttpServletRequest request) {
        return true;
    }

    @Override
    protected void beforeRequest(javax.servlet.http.HttpServletRequest request, String message) {
        MDC.put("url", request.getRequestURI());

        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        MDC.put("ip", ipAddress);

        //从redis等session存储里面获取userId并填充至MDC
        MDC.put("userId", "10000000");
    }

    @Override
    protected void afterRequest(javax.servlet.http.HttpServletRequest request, String message) {
        MDC.remove("url");
        MDC.remove("ip");
        MDC.remove("userId");
    }
}
