package hu.blaura.budgey.modules.transaction.service;

import hu.blaura.budgey.modules.classification.model.Classification;
import hu.blaura.budgey.modules.classification.model.dto.ClassificationDto;
import hu.blaura.budgey.modules.classification.service.ClassificationService;
import hu.blaura.budgey.modules.transaction.model.Transaction;
import hu.blaura.budgey.modules.transaction.model.TransactionCategory;
import hu.blaura.budgey.modules.transaction.model.dto.SummaryDto;
import hu.blaura.budgey.modules.transaction.model.dto.TransactionDto;
import hu.blaura.budgey.modules.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    public static final SimpleDateFormat ISO_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public Transaction create(TransactionDto dto) {
        Transaction transaction = new Transaction();
        transaction.setDate(dto.getDate());
        transaction.setCategory(dto.getCategory());
        transaction.setAmount(dto.getAmount());
        transaction.setTitle(dto.getTitle());
        return transactionRepository.save(transaction);
    }

    public Transaction getById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    public Transaction update(Long id, TransactionDto dto) {
        Transaction transaction = new Transaction();
        transaction.setId(id);
        transaction.setDate(dto.getDate());
        transaction.setCategory(dto.getCategory());
        transaction.setAmount(dto.getAmount());
        transaction.setTitle(dto.getTitle());
        return transactionRepository.save(transaction);
    }

    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }


    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    public List<SummaryDto> getWeeklySummaries(int take) {
        return getSummaries(take, "weekly");
    }

    public List<SummaryDto> getMonthlySummaries(int take) {
        return getSummaries(take, "monthly");
    }

    private List<SummaryDto> getSummaries(int take, String periodType) {
        List<SummaryDto> summaries = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        if (periodType.equals("weekly")) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        } else {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        }

        // Annyi hetet/honapot szedunk ki, amennyit a take parameter ker
        for (int i = 0; i < take; i++) {
            Date startOfPeriod = calendar.getTime();

            // Intervallum vege
            if (periodType.equals("weekly")) {
                calendar.add(Calendar.DAY_OF_WEEK, 6);
            } else {
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            }
            Date endOfPeriod = calendar.getTime();

            List<Transaction> periodTransactions = getTransactionsBetweenDates(startOfPeriod, endOfPeriod);
            summaries.add(calculateSummary(periodTransactions, startOfPeriod, endOfPeriod));

            // Visszamegyunk egy periodust
            calendar.setTime(startOfPeriod);
            calendar.add(periodType.equals("weekly") ? Calendar.WEEK_OF_YEAR : Calendar.MONTH, -1);
        }

        return summaries;
    }

    public List<Transaction> getTransactionsBetweenDates(Date startDate, Date endDate) {
        return transactionRepository.findAll().stream()
                .filter(transaction -> {
                    try {
                        Date transactionDate = ISO_DATE_FORMAT.parse(transaction.getDate());
                        return !transactionDate.before(startDate) && !transactionDate.after(endDate);
                    } catch (ParseException e) {
                        throw new RuntimeException("Failed to parse transaction date", e);
                    }
                })
                .collect(Collectors.toList());
    }

    private SummaryDto calculateSummary(List<Transaction> transactions, Date fromDate, Date toDate) {
        double income = transactions.stream()
                .filter(t -> t.getAmount() > 0)
                .mapToDouble(Transaction::getAmount)
                .sum();

        double expense = transactions.stream()
                .filter(t -> t.getAmount() < 0)
                .mapToDouble(t -> Math.abs(t.getAmount()))
                .sum();

        double profit = income - expense;

        String exportLink = String.format("/transaction/export?from=%s&to=%s",
                ISO_DATE_FORMAT.format(fromDate),
                ISO_DATE_FORMAT.format(toDate));

        return new SummaryDto(fromDate, toDate, income, expense, profit, exportLink);
    }

}
