package hu.blaura.budgey.utils;

import hu.blaura.budgey.modules.user.model.User;
import hu.blaura.budgey.modules.user.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
public class TokenInterceptor extends OncePerRequestFilter {
    @Autowired
    private TokenUtil jwtUtil;
    @Autowired
    private UserService userService; // Add this dependency

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // "Bearer " resz torlese

            String email = jwtUtil.getUsernameFromToken(token);

            if (email != null && jwtUtil.validateToken(token, email)) {

                final Optional<User> user = userService.findByEmail(email);

                if (user.isPresent()) {
                    // authentikaljuk
                    SecurityContextHolder.getContext().setAuthentication(
                            new UsernamePasswordAuthenticationToken(user.get(), null, List.of())
                    );
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
