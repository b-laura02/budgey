package hu.blaura.budgey.service;

import hu.blaura.budgey.model.dto.LoginDto;
import hu.blaura.budgey.model.dto.RegisterDto;
import hu.blaura.budgey.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    public String register(RegisterDto registerDto){
        // kapunk paramba email + jelszot + nevet
        // ebbol csinalunk egy usert az adatbazisba (repository)
        // majd csinalunk jwt tokent
        // visszaadjuk a tokent es a usert
        return "reg";
    }
    public String login(LoginDto loginDto){
        //kapunk paramba email+jelszot
        //letezik-e a user?
        //ha letezik: jo-e a jelszo
        //ha jo a jelszo: jwt tokent csinalunk--> azonositasra (token nelkul taka van)
        //visszaadjuk a tokent es a usert
        return "log";
    }
}
