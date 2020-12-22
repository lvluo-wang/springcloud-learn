package controller;

import application.HelloRemote;
import exception.BaseErrorCode;
import exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
@Slf4j
public class ConsumerController {


    @Autowired
    private HelloRemote helloRemote;
    @Autowired
    private MessageSource messageSource;


    @RequestMapping
    public String hello() {
//        try{
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        log.info("ConsumerController hello");

        String message = messageSource.getMessage("error.BASE_ERROR", new Object[0], LocaleContextHolder.getLocale());
        String message1 = messageSource.getMessage("error.CONSUMER_ERROR", new Object[0], LocaleContextHolder.getLocale());
        log.info("BASE_ERROR {}", message);
        log.info("CONSUMER_ERROR {}", message1);
        if (message1 != null) {
            throw new BaseException(BaseErrorCode.BASE_ERROR);
        }
        return helloRemote.hello();
    }
}
