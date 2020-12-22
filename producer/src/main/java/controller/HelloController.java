package controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/hello")
@Slf4j
public class HelloController {

//    private static final Log log = LogFactory.getLog(HelloController.class);

    @RequestMapping
    public String hello() {
//        throw new RuntimeException("xxxxx");
        log.info("Producer hello");
        return "Hello World! PRODUCER";
    }
}
