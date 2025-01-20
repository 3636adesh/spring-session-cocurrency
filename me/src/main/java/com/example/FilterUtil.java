package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

public class FilterUtil {

    private FilterUtil() {}


    public static void generateForbidden(HttpServletRequest request, HttpServletResponse response, ObjectMapper objectMapper, MessageSource messageSource) throws IOException {
        generateUnauthorized(request, response, objectMapper, messageSource, null);
    }

    public static void generateUnauthorized(HttpServletRequest request, HttpServletResponse response, ObjectMapper objectMapper, MessageSource messageSource, AuthenticationException ex) throws IOException {
        if (!response.isCommitted()) {
            if (ex instanceof DisabledException) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                objectMapper.writeValue(
                        response.getWriter(),
                        ApiResponse.getInstance(
                                ApiCode.ACCOUNT_DISABLED,
                                MessageUtil.getMessage(messageSource, ApiMessages.ACCOUNT_DISABLED)
                        )
                );
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                objectMapper.writeValue(
                        response.getWriter(),
                        ApiResponse.getInstance(
                                ApiCode.UNAUTHORIZED,
                                MessageUtil.getMessage(messageSource, ApiMessages.UNAUTHORIZED)
                        )
                );
            }
        }
    }

    public static void generateUnauthorized(HttpServletRequest request, HttpServletResponse response, ObjectMapper objectMapper, MessageSource messageSource) throws IOException {
        if (!response.isCommitted()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            objectMapper.writeValue(
                    response.getWriter(),
                    ApiResponse.getInstance(
                            ApiCode.UNAUTHORIZED,
                            MessageUtil.getMessage(messageSource, ApiMessages.UNAUTHORIZED)
                    )
            );
        }
    }


    public static void generateSuccess(HttpServletResponse response, ObjectMapper objectMapper, MessageSource messageSource) throws IOException {
        if (!response.isCommitted()) {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            objectMapper.writeValue(
                    response.getWriter(),
                    ApiResponse.getInstance(
                            ApiCode.SUCCESS,
                            MessageUtil.getMessage(messageSource, ApiMessages.SUCCESS)
                    )
            );
        }
    }


}
