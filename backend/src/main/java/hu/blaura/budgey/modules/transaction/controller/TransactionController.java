package hu.blaura.budgey.modules.transaction.controller;

import hu.blaura.budgey.modules.transaction.model.Transaction;
import hu.blaura.budgey.modules.transaction.model.dto.SummaryDto;
import hu.blaura.budgey.modules.transaction.model.dto.TransactionDto;
import hu.blaura.budgey.modules.transaction.service.CsvService;
import hu.blaura.budgey.modules.transaction.service.TransactionService;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Transaction> create(@RequestBody TransactionDto transactionDto) {
        return ResponseEntity.ok(transactionService.create(transactionDto));
    }

    @GetMapping("")
    public ResponseEntity<List<Transaction>> readAll() {
        return ResponseEntity.ok(transactionService.getAll());
    }

    @GetMapping("/weekly")
    public ResponseEntity<List<SummaryDto>> getWeeklySummaries(
            @RequestParam(defaultValue = "5") int weeks) {

        if (weeks <= 0) {
            throw new IllegalArgumentException("Weeks parameter must be positive");
        }

        List<SummaryDto> summaries = transactionService.getWeeklySummaries(weeks);
        return ResponseEntity.ok(summaries);
    }

    @GetMapping("/monthly")
    public ResponseEntity<List<SummaryDto>> getMonthlySummaries(
            @RequestParam(defaultValue = "5") int months) {

        if (months <= 0) {
            throw new IllegalArgumentException("Months parameter must be positive");
        }

        List<SummaryDto> summaries = transactionService.getMonthlySummaries(months);
        return ResponseEntity.ok(summaries);
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

    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<Transaction>> importTransactions(
            @RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please upload a CSV file");
        }

        try {
            List<TransactionDto> transactionDtos = csvService.importTransactions(file);

            List<Transaction> createdTransactions = transactionDtos.stream()
                    .map(transactionService::create)
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
            @RequestParam String to) {
        try {
            Date fromDate = ISO_DATE_FORMAT.parse(from);
            Date toDate = ISO_DATE_FORMAT.parse(to);

            List<Transaction> transactions =
                    transactionService.getTransactionsBetweenDates(fromDate, toDate);

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