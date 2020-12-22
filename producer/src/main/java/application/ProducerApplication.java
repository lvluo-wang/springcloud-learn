package application;

import controller.HelloController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@EnableHystrix
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@Import({
        HelloController.class
})
public class ProducerApplication {

    public static void main(String args[]) {
        SpringApplication.run(ProducerApplication.class, args);
    }

}
