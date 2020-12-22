package application;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(value = "PRODUCER", fallback = HelloRemoteHystrix.class)
public interface HelloRemote {

    @GetMapping(value = "/hello")
    String hello();
}
