package com.example;

import lombok.Getter;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Component
@Getter
public class ApiErrorAttributes extends DefaultErrorAttributes {

    private final MessageSource messageSource;

    private final HttpStatus status = HttpStatus.BAD_REQUEST;
    private final String message;

    public ApiErrorAttributes(MessageSource messageSource) {
        this.messageSource = messageSource;
        message = MessageUtil.getMessage(messageSource, ApiMessages.UNKNOWN);

    }

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
        Map<String, Object> map = super.getErrorAttributes(webRequest, options);
        map.put("status", getStatus());
        map.put("message", getMessage());
        return map;
    }


}
