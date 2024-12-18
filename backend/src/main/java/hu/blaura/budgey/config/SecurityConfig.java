package hu.blaura.budgey.config;

import hu.blaura.budgey.utils.TokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private TokenInterceptor tokenInterceptor = new TokenInterceptor();

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // csrf-et ki kell kapcsolni
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/login", "/auth/register").permitAll() // a bejelentkezos endpointok publikusak
                        .anyRequest().authenticated() // a tobbit vedeni kell
                )
                .addFilterBefore(tokenInterceptor, UsernamePasswordAuthenticationFilter.class); // a tokenInterceptor fogja szurni a kereseket

        return http.build();
    }

}
