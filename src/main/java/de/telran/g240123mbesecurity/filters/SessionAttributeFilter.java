package de.telran.g240123mbesecurity.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(3)
public class SessionAttributeFilter implements Filter {

    private String attributeName = "customAttribute";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();

        Attribute attribute = (Attribute) session.getAttribute(attributeName);
        System.out.println("Received attribute - " + attribute);

        if (attribute == null) {
            attribute = new Attribute(1, "Valid attribute", true);
            System.out.println("Sent attribute - " + attribute);
        } else {
            if (attribute.isValid()) {
                attribute.setValid(false);
            }
        }

        if (!attribute.isValid()) {
            System.out.println("Attribute validation failed!");
            session.invalidate();
        } else {
            session.setAttribute(attributeName, attribute);
        }

        filterChain.doFilter(request, response);
    }
}