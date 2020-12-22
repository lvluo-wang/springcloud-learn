package filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import util.RequestUtil;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class MonitoringGatewayFilter implements GatewayFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(MonitoringGatewayFilter.class);

    @Override
    public int getOrder() {
        return -2;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI requestURI = exchange.getRequest().getURI();
        String requestUUID = UUID.randomUUID().toString();
        LocalDateTime beginDateTime = LocalDateTime.now();
        //添加x-request-uuid请求头
        ServerHttpRequest request = exchange.getRequest().mutate().header("x-request-uuid", requestUUID).build();
        ServerWebExchange newExchange = exchange.mutate().request(request).build();
        return chain.filter(newExchange).then(Mono.defer(() -> {
            if (logger.isInfoEnabled()) {
                LocalDateTime endDateTime = LocalDateTime.now();
                Duration duration = Duration.between(beginDateTime, endDateTime);
                String clientIp = RequestUtil.getClientIp(request);
                logger.info("请求的uuid={}，请求的ip={}，请求的url={}，消耗的时间{}ms", requestUUID, clientIp,
                        requestURI.getPath(), duration.toMillis());
            }
            return Mono.empty();
        }));
    }

}
