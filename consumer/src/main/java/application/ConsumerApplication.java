package application;

import controller.ConsumerController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@EnableHystrix
@EnableDiscoveryClient
@EnableFeignClients
//@EnableAutoConfiguration(exclude={MessageSourceAutoConfiguration.class})
@SpringBootApplication
@Import({
        ConsumerController.class
})
public class ConsumerApplication {

    public static void main(String args[]) {
        SpringApplication.run(ConsumerApplication.class, args);
    }


}
