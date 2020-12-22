package application;

import controller.FallbackController;
import filter.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableDiscoveryClient
@Import({
        FallbackController.class,
        RateLimitByIpGatewayFilter.class
})
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }


//    @Bean
    public RouteLocator customerRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(
                        r -> r.path("/fluent/customer/**")
                                .filters(f -> f.stripPrefix(2)
                                        .filter(new ElapsedFilter())
                                        .addResponseHeader("X-Response-Default-Foo", "Default-Bar"))
                                        .uri("lb://CONSUMER")
                                        .order(0)
                                        .id("customer_service")
                )
//               .route(r -> r.path("/serviceApi/*")
//                       .filters(f -> f.stripPrefix(1)
//                               .filter(new MonitoringGatewayFilter())
//                               .hystrix(config -> {
//                                   config.setName("fallbackcmd");
//                                   config.setFallbackUri("forward:/fallback");
//                               } )
//                       )
//                .uri("lb://CONSUMER"))
                .build();
    }

    @Bean(name = RemoteAddrKeyResolver.BEAN_NAME )
    public RemoteAddrKeyResolver remoteAddrKeyResolver() {
        return new RemoteAddrKeyResolver();
    }

    @Bean(name = "Elapsed" )
    public ElapsedGatewayFilterFactory elapsedGatewayFilterFactory() {
        return new ElapsedGatewayFilterFactory();
    }

    @Bean(name = "RateLimitByIp" )
    RateLimitByIpGatewayFilter rateLimitByIpGatewayFilter() {
        return new RateLimitByIpGatewayFilter();
    }
}



