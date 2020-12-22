package web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import web.auth.AuthorizedUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import org.springframework.http.HttpHeaders;

import static web.HttpHeaders.HEADER_AUTHORIZATION;


@Slf4j
public class WebHandlerInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //todo 校验user
        String auth = request.getHeader(HEADER_AUTHORIZATION);
        AuthorizedUser user = new AuthorizedUser();
        user.setUserId(1000L);
        request.setAttribute(ContextName.CONTEXT_USER, user);

        logRequest(request);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("RESPONSE \"{} {} {}\" {} {}",
                request.getMethod(),
                url(request),
                request.getProtocol(),
                response.getStatus(),
                Optional.ofNullable(response.getHeader(HttpHeaders.CONTENT_LENGTH)).orElse(""));
    }


    private void logRequest(HttpServletRequest request) {
        log.info("REQUEST {} {} {} {} {} {}",
                request.getMethod(),
                url(request),
                request.getProtocol(),
                request.getHeader("X-Forwarded-For"),
                request.getHeader(HttpHeaders.REFERER),
                request.getHeader(HttpHeaders.USER_AGENT));
    }

    private String url(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String queryString = request.getQueryString();
        if (!StringUtils.isEmpty(queryString)) {
            return requestURI + "?" + queryString;
        }
        return requestURI;
    }
}
