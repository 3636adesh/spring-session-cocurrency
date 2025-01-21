package com.example;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(users.get(username)).orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found.", username)));
    }


    private static final Map<String, User> users = new LinkedHashMap<>();


    public static final String USERNAME_1 = "john@live.in";
    public static final String USERNAME_2 = "mike@live.in";

    static {
        users.put(USERNAME_1, new User(USERNAME_1, "{noop}password", "john", Collections.singleton("ROLE_ADMIN")));
        users.put(USERNAME_2, new User(USERNAME_2, "{noop}password", "mike", Collections.singleton("ROLE_ADMIN")));
    }
}
