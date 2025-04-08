package hu.blaura.budgey.modules.classification.repository;

import hu.blaura.budgey.modules.classification.model.Classification;
import hu.blaura.budgey.modules.classification.model.enums.BalanceGroup;
import hu.blaura.budgey.modules.classification.model.enums.BalanceMainGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ClassificationRepository extends JpaRepository<Classification, Long> {
    Optional<Classification> findByBalanceMainGroupAndBalanceGroupAndBalanceItem(
            BalanceMainGroup balanceMainGroup,
            BalanceGroup balanceGroup,
            String balanceItem
    );

    List<Classification> findByBalanceMainGroupAndBalanceGroup(
            BalanceMainGroup balanceMainGroup,
            BalanceGroup balanceGroup
    );
}
