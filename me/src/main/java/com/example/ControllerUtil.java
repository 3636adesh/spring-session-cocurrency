package com.example;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

@UtilityClass
public class ControllerUtil {

    ResponseEntity<?> processError(MessageSource messageSource, HttpServletRequest request, Map<String, Object> extras) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ApiResponse.getInstance(
                                        ApiCode.UNKNOWN,
                                        MessageUtil.getMessage(messageSource, ApiMessages.UNKNOWN)
                                )
                                .addExtras(extras)
                );

    }
}
