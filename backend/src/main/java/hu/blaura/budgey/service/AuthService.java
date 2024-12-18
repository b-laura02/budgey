package hu.blaura.budgey.service;

import hu.blaura.budgey.model.User;
import hu.blaura.budgey.model.dto.AuthResponseDto;
import hu.blaura.budgey.model.dto.LoginDto;
import hu.blaura.budgey.model.dto.RegisterDto;
import hu.blaura.budgey.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder; // ezzel titkositjuk a jelszavakat

    @Value("${jwt.secret}")
    private String jwtSecret;

    public AuthResponseDto register(RegisterDto registerDto){
        // kapunk paramba email + jelszot + nevet
        // ebbol csinalunk egy usert az adatbazisba (repository)
        // majd csinalunk jwt tokent
        // visszaadjuk a tokent es a usert

        if (userRepository.findByEmail(registerDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));  // Encoding the password
        user.setFullName(registerDto.getFullName());

        userRepository.save(user);

        String token = generateToken(user);

        return new AuthResponseDto(token, user.getEmail(), user.getFullName());
    }
    public AuthResponseDto login(LoginDto loginDto){
        //kapunk paramba email+jelszot
        //letezik-e a user?
        //ha letezik: jo-e a jelszo
        //ha jo a jelszo: jwt tokent csinalunk--> azonositasra (token nelkul taka van)
        //visszaadjuk a tokent es a usert

        User user = userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String token = generateToken(user);

        return new AuthResponseDto(token, user.getEmail(), user.getFullName());
    }

    private String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("name", user.getFullName())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}
