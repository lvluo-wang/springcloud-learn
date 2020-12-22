package application;

import org.springframework.stereotype.Component;

@Component
public class HelloRemoteHystrix implements HelloRemote{

    @Override
    public String hello() {
        return "CONSUMER Hystrix";
    }
}
