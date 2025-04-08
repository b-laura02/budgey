package hu.blaura.budgey.modules.transaction.service;

import hu.blaura.budgey.modules.classification.model.Classification;
import hu.blaura.budgey.modules.classification.model.dto.ClassificationDto;
import hu.blaura.budgey.modules.classification.service.ClassificationService;
import hu.blaura.budgey.modules.transaction.model.Transaction;
import hu.blaura.budgey.modules.transaction.model.dto.TransactionDto;
import hu.blaura.budgey.modules.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final ClassificationService classificationService;

    private Classification findOrCreateClassification(ClassificationDto dto) {
        Optional<Classification> fetchedClassification = classificationService.find(dto);
        return fetchedClassification.orElse(classificationService.create(dto));
    }

    public Transaction create(TransactionDto dto) {
        Classification classification = findOrCreateClassification(dto.getClassification());

        Transaction transaction = new Transaction();
        transaction.setType(dto.getType());
        transaction.setClassification(classification);
        transaction.setAmount(dto.getAmount());
        transaction.setTitle(dto.getTitle());
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getAllWeekly() {
        return transactionRepository.findAll();
    }

    public List<Transaction> getAllMonthly() {
        return transactionRepository.findAll();
    }

    public Transaction getById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    public Transaction update(Long id, TransactionDto dto) {
        Classification classification = findOrCreateClassification(dto.getClassification());

        Transaction transaction = getById(id);
        transaction.setType(dto.getType());
        transaction.setClassification(classification);
        transaction.setAmount(dto.getAmount());
        transaction.setTitle(dto.getTitle());
        return transactionRepository.save(transaction);
    }

    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }
}
