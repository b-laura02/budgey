package hu.blaura.budgey.modules.classification.controller;

import hu.blaura.budgey.modules.classification.model.dto.BalanceGroupDto;
import hu.blaura.budgey.modules.classification.model.enums.BalanceGroup;
import hu.blaura.budgey.modules.classification.model.enums.BalanceMainGroup;
import hu.blaura.budgey.modules.classification.service.ClassificationService;
import hu.blaura.budgey.modules.transaction.model.Transaction;
import hu.blaura.budgey.modules.transaction.model.dto.TransactionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classification")
@RequiredArgsConstructor
@Validated
public class ClassificationController {
    private final ClassificationService classificationService;

    @GetMapping("/items")
    public ResponseEntity<List<String>> getBalanceItemsByGroups(
            @RequestParam BalanceMainGroup balanceMainGroup,
            @RequestParam BalanceGroup balanceGroup) {
        return ResponseEntity.ok(classificationService.getBalanceItemsByGroups(balanceMainGroup, balanceGroup));
    }

    @GetMapping("/groups")
    public ResponseEntity<List<BalanceGroup>> getBalanceGroupsByBalanceMainGroup(@RequestParam BalanceMainGroup balanceMainGroup) {
        return ResponseEntity.ok(classificationService.getBalanceGroupsByBalanceMainGroup(balanceMainGroup));
    }
}