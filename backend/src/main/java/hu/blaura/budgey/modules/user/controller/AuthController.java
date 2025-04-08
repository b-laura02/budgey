package hu.blaura.budgey.modules.user.controller;

import hu.blaura.budgey.modules.user.model.dto.AuthResponseDto;
import hu.blaura.budgey.modules.user.model.dto.LoginDto;
import hu.blaura.budgey.modules.user.model.dto.RegisterDto;
import hu.blaura.budgey.modules.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterDto registerDto){
        return ResponseEntity.ok().body(authService.register(registerDto)); //sablon hivasa
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto){
        //return ResponseEntity.badRequest().body("Rossz jelszó"); rossz jelszo eseten elm. 404
        return ResponseEntity.ok().body(authService.login(loginDto));

    }
}
