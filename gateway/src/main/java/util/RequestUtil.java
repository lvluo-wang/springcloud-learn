package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;

public class RequestUtil {

    private static Logger logger = LoggerFactory.getLogger(RequestUtil.class);

    /**
     * 获取请求主机IP地址，若通过代理进来则透过防火墙获取真实IP地址
     *
     * @param request
     * @return
     */
    public static String getClientIp(ServerHttpRequest request) {
        try {
            String ip = request.getHeaders().getFirst("X-Real-IP");

            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeaders().getFirst("X-Forwarded-For");
                }

                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeaders().getFirst("Proxy-Client-IP");
                }

                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeaders().getFirst("WL-Proxy-Client-IP");
                }

                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeaders().getFirst("HTTP-Client-IP");
                }

                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getHeaders().getFirst("HTTP-X-Forwarded-For");
                }

                if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                    ip = request.getRemoteAddress().getHostString();
                }
            } else if (ip.length() > 15) {
                String[] ips = ip.split(",");
                for (int index = 0; index < ips.length; index++) {
                    String strIP = (String) ips[index];
                    if (!("unknown".equalsIgnoreCase(strIP))) {
                        ip = strIP;
                        break;
                    }
                }
            }
            return ip;
        } catch (Exception e) {
            logger.error("获取IP地址发生错误", e);
            return "";
        }
    }
}
