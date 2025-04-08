package hu.blaura.budgey.modules.tip.controller;

import hu.blaura.budgey.modules.tip.model.Tip;
import hu.blaura.budgey.modules.tip.model.dto.TipDto;
import hu.blaura.budgey.modules.tip.service.TipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tip")
@RequiredArgsConstructor
@Validated
public class TipController {
    private final TipService tipService;

    @PostMapping("")
    public ResponseEntity<Tip> create(@RequestBody TipDto tipDto) {
        return ResponseEntity.ok(tipService.create(tipDto));
    }

    @GetMapping("")
    public ResponseEntity<List<Tip>> readAll() {
        return ResponseEntity.ok(tipService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tip> read(@PathVariable Long id) {
        return ResponseEntity.ok(tipService.getById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Tip> update(@PathVariable Long id, @RequestBody TipDto tipDto) {
        return ResponseEntity.ok(tipService.update(id, tipDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tipService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
