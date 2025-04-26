package hu.blaura.budgey.modules.transaction.service;

import hu.blaura.budgey.modules.transaction.model.Transaction;
import hu.blaura.budgey.modules.transaction.model.dto.SummaryDto;
import hu.blaura.budgey.modules.transaction.model.dto.TransactionDto;
import hu.blaura.budgey.modules.transaction.repository.TransactionRepository;
import hu.blaura.budgey.modules.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    public static final SimpleDateFormat ISO_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public Transaction create(TransactionDto dto, User user) {
        Transaction transaction = new Transaction();
        transaction.setDate(dto.getDate());
        transaction.setCategory(dto.getCategory());
        if (dto.getCategory().isExpense()) {
            transaction.setAmount(-dto.getAmount());
        } else {
            transaction.setAmount(dto.getAmount());
        }
        transaction.setTitle(dto.getTitle());
        transaction.setUser(user);
        return transactionRepository.save(transaction);
    }

    public Transaction getById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    public Transaction update(Long id, TransactionDto dto, User user) {
        Transaction transaction = new Transaction();
        transaction.setId(id);
        transaction.setDate(dto.getDate());
        transaction.setCategory(dto.getCategory());
        if (dto.getCategory().isExpense()) {
            transaction.setAmount(-dto.getAmount());
        } else {
            transaction.setAmount(dto.getAmount());
        }
        transaction.setTitle(dto.getTitle());
        transaction.setUser(user);
        return transactionRepository.save(transaction);
    }

    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }


    public List<Transaction> getAll(int offset, int take, User user) {
        PageRequest offsetRequest = PageRequest.of(offset / take, take);
        return transactionRepository.findByUserOrderByDateDesc(user, offsetRequest).getContent();
    }

    public List<SummaryDto> getWeeklySummaries(int offset, int take, User user) {
        return getSummaries(offset, take, "weekly", user);
    }

    public List<SummaryDto> getMonthlySummaries(int offset, int take, User user) {
        return getSummaries(offset, take, "monthly", user);
    }



    private List<SummaryDto> getSummaries(int offset, int take, String periodType, User user) {
        List<SummaryDto> summaries = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();

        if (periodType.equals("weekly")) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        } else {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
        }

        // ha van offset az annyit jelent, hogy nem az első oldal adatai kellenek
        // azaz régebbi adatokat kérünk le
        calendar.add(periodType.equals("weekly") ? Calendar.WEEK_OF_YEAR : Calendar.MONTH, -offset);

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

            List<Transaction> periodTransactions = getTransactionsBetweenDates(startOfPeriod, endOfPeriod, user);
            summaries.add(calculateSummary(periodTransactions, startOfPeriod, endOfPeriod));

            // Visszamegyunk egy periodust
            calendar.setTime(startOfPeriod);
            calendar.add(periodType.equals("weekly") ? Calendar.WEEK_OF_YEAR : Calendar.MONTH, -1);
        }

        return summaries;
    }

    public List<Transaction> getTransactionsBetweenDates(Date startDate, Date endDate, User user) {
//        return transactionRepository.findAll().stream()
//                .filter(transaction -> {
//                    try {
//                        Date transactionDate = ISO_DATE_FORMAT.parse(transaction.getDate());
//                        return !transactionDate.before(startDate) && !transactionDate.after(endDate);
//                    } catch (ParseException e) {
//                        throw new RuntimeException("Failed to parse transaction date", e);
//                    }
//                })
//                .collect(Collectors.toList());
        return transactionRepository.findByUserAndDateBetween(
                user,
                ISO_DATE_FORMAT.format(startDate),
                ISO_DATE_FORMAT.format(endDate)
        );
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
