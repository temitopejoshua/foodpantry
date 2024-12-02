package edu.bowiestateuni.groupproj.foodpantry.services.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
@Configuration
public class CSPFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletResponse instanceof HttpServletResponse httpResponse) {
            httpResponse.setHeader("Content-Security-Policy",
                    "default-src 'self'; script-src 'self'; object-src 'none'; style-src 'self' 'unsafe-inline'; img-src 'self';");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
