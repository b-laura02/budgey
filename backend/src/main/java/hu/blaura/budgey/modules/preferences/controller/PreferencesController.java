package hu.blaura.budgey.modules.preferences.controller;

import hu.blaura.budgey.modules.preferences.model.Preferences;
import hu.blaura.budgey.modules.preferences.model.dto.PreferencesDto;
import hu.blaura.budgey.modules.preferences.service.PreferencesService;
import hu.blaura.budgey.modules.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/preferences")
@RequiredArgsConstructor
@Validated
public class PreferencesController {
    private final PreferencesService preferencesService;

    @GetMapping("")
    public ResponseEntity<Preferences> read(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(preferencesService.getByUser(user));
    }

    @PatchMapping("")
    public ResponseEntity<Preferences> update(
            @RequestBody PreferencesDto preferencesDto,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(preferencesService.update(user, preferencesDto));
    }
}
