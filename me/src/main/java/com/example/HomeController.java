package com.example;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class HomeController {

    private static final String HOME_VIEW_COUNT = "HOME_VIEW_COUNT";

    @GetMapping("/")
    public String home(Principal principal, HttpSession session) {
        incrementCounter(session,HOME_VIEW_COUNT);
        return HOME_VIEW_COUNT;
    }

    @GetMapping("/count")
    public String count(HttpSession session) {
        return HOME_VIEW_COUNT+" : "+session.getAttribute(HOME_VIEW_COUNT);
    }

    private void incrementCounter(HttpSession session,String attribute) {
        int viewCount = session.getAttribute(attribute) == null ? 0 : (Integer) session.getAttribute(attribute);
        session.setAttribute(attribute, ++viewCount);
    }
}
