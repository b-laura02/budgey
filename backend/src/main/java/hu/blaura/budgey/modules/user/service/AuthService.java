package hu.blaura.budgey.modules.user.service;

import hu.blaura.budgey.modules.preferences.model.Preferences;
import hu.blaura.budgey.modules.preferences.repository.PreferencesRepository;
import hu.blaura.budgey.modules.user.model.User;
import hu.blaura.budgey.modules.user.model.dto.AuthResponseDto;
import hu.blaura.budgey.modules.user.model.dto.LoginDto;
import hu.blaura.budgey.modules.user.model.dto.RegisterDto;
import hu.blaura.budgey.modules.user.repository.UserRepository;
import hu.blaura.budgey.utils.TokenUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PreferencesRepository preferencesRepository;
    private final BCryptPasswordEncoder passwordEncoder; // ezzel titkositjuk a jelszavakat
    private final TokenUtil tokenUtil;

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
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setFullName(registerDto.getFullName());

        userRepository.save(user);

        Preferences preferences = new Preferences();
        preferences.setUser(user);
        preferences.setTargetIncome(0);
        preferences.setTargetExpense(0);
        preferences.setTargetProfit(0);
        preferences.setAllowAIProcessing(false);
        preferencesRepository.save(preferences);

        String token = tokenUtil.generateToken(user);

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

        String token = tokenUtil.generateToken(user);

        return new AuthResponseDto(token, user.getEmail(), user.getFullName());
    }
}
