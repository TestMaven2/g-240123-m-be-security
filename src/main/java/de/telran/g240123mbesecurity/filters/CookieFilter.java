package de.telran.g240123mbesecurity.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;

@Component
@Order(1)
public class CookieFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Cookie[] cookies = request.getCookies();
        Cookie customCookie = null;

        if (cookies != null) {
            customCookie = Arrays.stream(cookies)
                    .filter(x -> x.getName().equals("USER_STATUS"))
                    .findFirst()
                    .orElse(null);
        }

        if (customCookie == null) {
            customCookie = new Cookie("USER_STATUS", "NEW_USER");
        } else {
            customCookie.setValue("OLD_USER");
        }

        System.out.println("User status = " + customCookie.getValue());
        response.addCookie(customCookie);

        filterChain.doFilter(request, response);
    }
}