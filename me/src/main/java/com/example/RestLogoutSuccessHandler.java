package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RestLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {


    private final MessageSource messageSource;
    private final ObjectMapper objectMapper;
    public RestLogoutSuccessHandler(MessageSource messageSource, ObjectMapper objectMapper) {
        this.messageSource = messageSource;
        this.objectMapper = objectMapper;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        FilterUtil.generateSuccess(response, objectMapper, messageSource);
    }
}
