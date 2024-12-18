package hu.blaura.budgey.controller;

import hu.blaura.budgey.model.dto.LoginDto;
import hu.blaura.budgey.model.dto.RegisterDto;
import hu.blaura.budgey.service.AuthService;
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
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        return ResponseEntity.ok().body(authService.register(registerDto)); //sablon hivasa
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto){
        //return ResponseEntity.badRequest().body("Rossz jelszó"); rossz jelszo eseten elm. 404
        return ResponseEntity.ok().body(authService.login(loginDto));

    }
}
