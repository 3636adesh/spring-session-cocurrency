package com.example;

import lombok.experimental.UtilityClass;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.validation.FieldError;

import java.util.Locale;

@UtilityClass
public class MessageUtil {
    String getMessage(MessageSource messageSource, String key) {
        return messageSource.getMessage(key, null, "", getLocale());
    }


    String getMessage(MessageSource messageSource, String key, String... args) {
        return messageSource.getMessage(key, args, "", getLocale());
    }


   String getMessage(MessageSource messageSource, FieldError error) {
        try {
            return messageSource.getMessage(error, getLocale());
        } catch (NoSuchMessageException ex) {
            return "";
        }
    }

   Locale getLocale() {
        return Locale.getDefault();
    }
}
