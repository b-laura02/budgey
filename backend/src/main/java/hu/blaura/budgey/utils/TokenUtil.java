package hu.blaura.budgey.utils;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;

public class TokenUtil {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public boolean validateToken(String token, String username) {
        return username.equals(getUsernameFromToken(token));
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .build()
                .parseSignedClaims(token)
                .getBody()
                .getSubject();
    }
}

