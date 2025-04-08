package hu.blaura.budgey.modules.transaction.controller;

import hu.blaura.budgey.modules.transaction.model.Transaction;
import hu.blaura.budgey.modules.transaction.model.dto.TransactionDto;
import hu.blaura.budgey.modules.transaction.service.TransactionService;
import hu.blaura.budgey.modules.user.model.dto.AuthResponseDto;
import hu.blaura.budgey.modules.user.model.dto.LoginDto;
import hu.blaura.budgey.modules.user.model.dto.RegisterDto;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
@Validated
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("")
    public ResponseEntity<Transaction> create(@RequestBody TransactionDto transactionDto) {
        return ResponseEntity.ok(transactionService.create(transactionDto));
    }

    @GetMapping("")
    public ResponseEntity<List<Transaction>> readAll() {
        return ResponseEntity.ok(transactionService.getAll());
    }
    @GetMapping("/weekly")
    public ResponseEntity<List<Transaction>> readAllWeekly() {
        return ResponseEntity.ok(transactionService.getAllWeekly());
    }
    @GetMapping("/monthly")
    public ResponseEntity<List<Transaction>> readAllMonthly() {
        return ResponseEntity.ok(transactionService.getAllMonthly());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> read(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Transaction> update(@PathVariable Long id, @RequestBody TransactionDto transactionDto) {
        return ResponseEntity.ok(transactionService.update(id, transactionDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

//read ne post legyen hanem get
//az update patch legyen
//a delete delete legyen
//dto, serviceeket