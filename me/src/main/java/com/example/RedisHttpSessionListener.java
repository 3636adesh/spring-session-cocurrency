package com.example;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class RedisHttpSessionListener implements HttpSessionListener {

    private final ApplicationContext applicationContext;

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        RedisIndexedSessionRepository sessionRepository =
                applicationContext.getBean(RedisIndexedSessionRepository.class);

        Map<String, ?> sessions = sessionRepository.findByIndexNameAndIndexValue(
                FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME,
                CustomUserDetailsService.USERNAME_1
        );

        System.out.printf("Number of sessions for username %s: %d%n",
                CustomUserDetailsService.USERNAME_1, sessions.size());
        System.out.printf("Session Created: %s%n", event.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        var sessionRepository = applicationContext.getBean(RedisIndexedSessionRepository.class);
        Map<String, ?> sessions = sessionRepository.findByIndexNameAndIndexValue(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME, CustomUserDetailsService.USERNAME_1);
        System.out.printf("Number of sessions for username %s: %d%n",CustomUserDetailsService.USERNAME_1, sessions.size());
        System.out.printf("Session Destroyed: %s%n",event.getSession().getId());
    }
}
