package com.example;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisIndexedHttpSession;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

@Configuration
@RequiredArgsConstructor
@EnableRedisIndexedHttpSession
public class WebSecurityConfig {

    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final AccessDeniedHandler restAccessDeniedHandler;
    private final RedisIndexedSessionRepository sessionRepository;
    private final RestLogoutSuccessHandler restLogoutSuccessHandler;
    private final FilterConfig filterConfig;
    private final CustomAuthenticationManager authenticationManager;


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authz ->
                                authz
                                        .requestMatchers("/me").hasAuthority("ROLE_ADMIN")
                                        .anyRequest().denyAll()
                )
                .logout(logout -> logout.logoutSuccessHandler(restLogoutSuccessHandler))
                .addFilterAt(filterConfig.usernamePasswordAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(
                        exception ->
                                exception.authenticationEntryPoint(restAuthenticationEntryPoint)
                                        .accessDeniedHandler(restAccessDeniedHandler)
                )
                .sessionManagement(session -> session.maximumSessions(2)
                        .maxSessionsPreventsLogin(true)
                        .sessionRegistry(sessionRegistry()))
                .build();
    }

    @Bean
    SpringSessionBackedSessionRegistry<RedisIndexedSessionRepository.RedisSession> sessionRegistry() {
        return new SpringSessionBackedSessionRegistry<>(this.sessionRepository);
    }

}
