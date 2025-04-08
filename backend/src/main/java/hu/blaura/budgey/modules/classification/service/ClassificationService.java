package hu.blaura.budgey.modules.classification.service;

import hu.blaura.budgey.modules.classification.model.Classification;
import hu.blaura.budgey.modules.classification.model.dto.BalanceGroupDto;
import hu.blaura.budgey.modules.classification.model.dto.ClassificationDto;
import hu.blaura.budgey.modules.classification.model.enums.BalanceGroup;
import hu.blaura.budgey.modules.classification.model.enums.BalanceMainGroup;
import hu.blaura.budgey.modules.classification.repository.ClassificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassificationService {
    private final ClassificationRepository classificationRepository;

    public Classification create(ClassificationDto classificationDto) {
        return classificationRepository.save(Classification.fromDto(classificationDto));
    }

    /// Lekerdezi a csoportositasokat minden adat alapjan
    public Optional<Classification> find(ClassificationDto classificationDto) {
        return classificationRepository.findByBalanceMainGroupAndBalanceGroupAndBalanceItem(
                classificationDto.getBalanceMainGroup(),
                classificationDto.getBalanceGroup(),
                classificationDto.getBalanceItem());
    }

    /// Lekerdezi a csoportositasokat a merlegfocsoport es merlegcsoport alapjan
    public List<String> getBalanceItemsByGroups(BalanceMainGroup balanceMainGroup,BalanceGroup balanceGroup) {
        List<Classification> classifications = classificationRepository.findByBalanceMainGroupAndBalanceGroup(
                balanceMainGroup,
                balanceGroup);
        return classifications
                .stream()
                .map(Classification::getBalanceItem)
                .collect(Collectors.toList());
    }

    /// Visszaadja a merlegcsoportokat a merlegfocsoport alapjan
    public List<BalanceGroup> getBalanceGroupsByBalanceMainGroup(BalanceMainGroup balanceMainGroup) {
        return BalanceGroup.getByMainGroup(balanceMainGroup);
    }

    public Classification getById(Long id) {
        return classificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }
}
