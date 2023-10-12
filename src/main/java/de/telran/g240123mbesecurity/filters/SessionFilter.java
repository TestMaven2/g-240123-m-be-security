package de.telran.g240123mbesecurity.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(2)
public class SessionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();

        Integer counter = (Integer) session.getAttribute("counter");
        counter = counter == null ? 0 : counter;

        System.out.println("Requests count - " + counter);

        if (counter == 5) {
            System.out.println("Too many requests!");
            session.invalidate();
        } else {
            session.setAttribute("counter", ++counter);
        }

        filterChain.doFilter(request, response);
    }
}