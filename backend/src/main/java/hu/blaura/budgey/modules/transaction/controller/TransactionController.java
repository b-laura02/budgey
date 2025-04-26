package hu.blaura.budgey.modules.transaction.controller;

import hu.blaura.budgey.modules.transaction.model.Transaction;
import hu.blaura.budgey.modules.transaction.model.dto.SummaryDto;
import hu.blaura.budgey.modules.transaction.model.dto.TransactionDto;
import hu.blaura.budgey.modules.transaction.service.CsvService;
import hu.blaura.budgey.modules.transaction.service.TransactionService;

import hu.blaura.budgey.modules.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static hu.blaura.budgey.modules.transaction.service.TransactionService.ISO_DATE_FORMAT;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
@Validated
public class TransactionController {
    private final TransactionService transactionService;
    private final CsvService csvService;

    @PostMapping("")
    public ResponseEntity<Transaction> create(
            @RequestBody TransactionDto transactionDto,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(transactionService.create(transactionDto, user));
    }

    @GetMapping("")
    public ResponseEntity<List<Transaction>> readAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int take,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(transactionService.getAll(page, take, user));
    }

    @GetMapping("/weekly")
    public ResponseEntity<List<SummaryDto>> getWeeklySummaries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int take,
            @AuthenticationPrincipal User user
    ) {

        if (take <= 0) {
            throw new IllegalArgumentException("Weeks parameter must be positive");
        }
        if (page < 0) {
            throw new IllegalArgumentException("page parameter must be non-negative");
        }

        List<SummaryDto> summaries = transactionService.getWeeklySummaries(page, take, user);
        return ResponseEntity.ok(summaries);
    }

    @GetMapping("/monthly")
    public ResponseEntity<List<SummaryDto>> getMonthlySummaries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int take,
            @AuthenticationPrincipal User user
    ) {

        if (take <= 0) {
            throw new IllegalArgumentException("Months parameter must be positive");
        }
        if (page < 0) {
            throw new IllegalArgumentException("page parameter must be non-negative");
        }

        List<SummaryDto> summaries = transactionService.getMonthlySummaries(page, take, user);
        return ResponseEntity.ok(summaries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> read(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(transactionService.getById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Transaction> update(
            @PathVariable Long id,
            @RequestBody TransactionDto transactionDto,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(transactionService.update(id, transactionDto, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal User user
    ) {
        transactionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<Transaction>> importTransactions(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal User user
    ) {

        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please upload a CSV file");
        }

        try {
            List<TransactionDto> transactionDtos = csvService.importTransactions(file);

            List<Transaction> createdTransactions = transactionDtos.stream()
                    .map(dto -> transactionService.create(dto, user))
                    .toList();
            return ResponseEntity.ok(createdTransactions);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to process CSV file: " + e.getMessage());
        }
    }

    @GetMapping("/export")
    public ResponseEntity<Resource> exportTransactions(
            @RequestParam String from,
            @RequestParam String to,
            @AuthenticationPrincipal User user
    ) {
        try {
            Date fromDate = ISO_DATE_FORMAT.parse(from);
            Date toDate = ISO_DATE_FORMAT.parse(to);

            List<Transaction> transactions =
                    transactionService.getTransactionsBetweenDates(fromDate, toDate, user);

            String csv = csvService.generateTransactionsCsv(transactions);

            ByteArrayResource resource = new ByteArrayResource(csv.getBytes());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=transactions_%s_%s.csv".formatted(from, to))
                    .contentType(MediaType.parseMediaType("text/csv"))
                    .body(resource);

        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format");
        }
    }
}

//read ne post legyen hanem get
//az update patch legyen
//a delete delete legyen
//dto, serviceeket