package hu.blaura.budgey.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.List;

public class TokenInterceptor extends UsernamePasswordAuthenticationFilter {
    private TokenUtil jwtUtil = new TokenUtil();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String token = ((HttpServletRequest) request).getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " resz torlese

            String username = jwtUtil.getUsernameFromToken(token);
            if (username != null && jwtUtil.validateToken(token, username)) {
                // authentikaljuk
                SecurityContextHolder.getContext().setAuthentication(
                        new UsernamePasswordAuthenticationToken(username, null, List.of())
                );
            }
        }

        chain.doFilter(request, response);
    }
}
