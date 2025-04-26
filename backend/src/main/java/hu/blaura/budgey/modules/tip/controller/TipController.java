package hu.blaura.budgey.modules.tip.controller;

import hu.blaura.budgey.modules.preferences.model.Preferences;
import hu.blaura.budgey.modules.preferences.service.PreferencesService;
import hu.blaura.budgey.modules.tip.model.Tip;
import hu.blaura.budgey.modules.tip.model.dto.TipDto;
import hu.blaura.budgey.modules.tip.service.TipService;
import hu.blaura.budgey.modules.transaction.model.Transaction;
import hu.blaura.budgey.modules.transaction.service.TransactionService;
import hu.blaura.budgey.modules.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tip")
@RequiredArgsConstructor
@Validated
public class TipController {
    private final TipService tipService;

    // ez csak tesztelesre van bent
    @PostMapping("")
    public ResponseEntity<List<Tip>> generateTips(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(tipService.generate(user));
    }

    @GetMapping("")
    public ResponseEntity<List<Tip>> readAll(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(tipService.getAll(user));
    }
}
