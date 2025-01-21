package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;

@RequiredArgsConstructor
@Configuration
public class FilterConfig {

    private final ObjectMapper objectMapper;
    private final RestSimpleUrlAuthenticationFailureHandler restAuthenticationFailureHandler;
    private final RestSavedRequestAwareAuthenticationSuccessHandler restAuthenticationSuccessHandler;

    @Autowired
    @Lazy
    private  SessionRegistry sessionRegistry;

    protected RestUsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        RestUsernamePasswordAuthenticationFilter filter = new RestUsernamePasswordAuthenticationFilter(objectMapper);
        filter.setAuthenticationManager(authenticationManager);
        filter.setAuthenticationFailureHandler(restAuthenticationFailureHandler);
        filter.setAuthenticationSuccessHandler(restAuthenticationSuccessHandler);
        filter.setSessionAuthenticationStrategy(authStrategy());
        return filter;
    }

    private ConcurrentSessionControlAuthenticationStrategy authStrategy() {
        ConcurrentSessionControlAuthenticationStrategy result = new ConcurrentSessionControlAuthenticationStrategy(
                sessionRegistry);
        result.setExceptionIfMaximumExceeded(true);
        return result;
    }
}
