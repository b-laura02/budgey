package hu.blaura.budgey.modules.user.service;

import hu.blaura.budgey.modules.user.model.User;
import hu.blaura.budgey.modules.user.model.dto.AuthResponseDto;
import hu.blaura.budgey.modules.user.model.dto.LoginDto;
import hu.blaura.budgey.modules.user.model.dto.RegisterDto;
import hu.blaura.budgey.modules.user.repository.UserRepository;
import hu.blaura.budgey.utils.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
