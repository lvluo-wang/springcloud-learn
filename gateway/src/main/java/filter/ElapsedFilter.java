package filter;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class ElapsedFilter implements GatewayFilter, Ordered {

    private static final Log log = LogFactory.getLog(GatewayFilter.class);


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put("elapsedBeginTime", System.currentTimeMillis());

        return chain.filter(exchange).then(
                Mono.fromRunnable( () -> {
                    Long beginTime = exchange.getAttribute("elapsedBeginTime");
                    if (beginTime != null) {
                        log.info(exchange.getRequest().getURI().getRawPath() + ":" + (System.currentTimeMillis()-beginTime) + "ms");
                    }
                })
        );
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
