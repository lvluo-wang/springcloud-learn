package web;

import exception.BaseErrorCode;
import exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;


@ControllerAdvice
public class WebExceptionHandlerAdapter {

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ErrorAttributes errorAttributes;


    @ExceptionHandler
    public ResponseEntity<Map<String,Object>> handle(WebRequest request, Exception ex) {
        BaseException baseException = translate(ex);
        baseException.convertMessage(messageSource);
        Map<String, Object> responses = errorAttributes.getErrorAttributes(request, true);
        responses.put("message", baseException.getMessage());
        responses.put("code", baseException.geCodeName());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return new ResponseEntity<>(responses, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private BaseException translate(Exception ex) {
        if (ex instanceof BaseException) {
            return (BaseException) ex;
        }
        return new BaseException(ex, BaseErrorCode.UNKNOWN_EXCEPTION);
    }

}
